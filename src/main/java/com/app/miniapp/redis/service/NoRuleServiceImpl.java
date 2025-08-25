package com.app.miniapp.redis.service;



import com.app.miniapp.redis.dto.CommonConstant;
import com.app.miniapp.redis.dto.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * PURPOSE:
 * DESCRIPTION: 上传批次号序列生成类
 * CALLED BY:
 * CREATE DATE: 2023年3月29日
 * UPDATE DATE:
 * HISTORY:
 * @author fansen
 * @version 1.0
 * @since java 1.8
 */
@Service
public class NoRuleServiceImpl implements INoRuleService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * ID数目的低水平线，ID少于此数目后程序自动去缓存中获取
     */
    public static final int LOW_LEVEL_COUNT = 10;

    private static Map<String, Queue<Integer>> SER_NO_QUEUE = new ConcurrentHashMap<String, Queue<Integer>>(CommonConstant.NUM_COMMON_8);

    /**
     * 存储锁对象
     */
    private static ConcurrentHashMap<String, Object> lockMap = new ConcurrentHashMap<>(CommonConstant.NUM_COMMON_8);

    private static int ONCE_FETCH_COUNT = 10;

    /**
     * 24*60*60*1000 ms
     */
    public static final int TIME_CYCLE = 86400000;

    // 保存每个 key 对应的最后清空日期
    private static Map<String, AtomicReference<String>> lastResetDateMap = new ConcurrentHashMap<>();

    @Override
    public String getBatchNo(String key, String date) {
        lastResetDateMap.putIfAbsent(key, new AtomicReference<>(date));
        Queue<Integer> serialNumberQueue = new LinkedBlockingQueue<>(ONCE_FETCH_COUNT + LOW_LEVEL_COUNT);
        for (int i = 5; i <= 9; i++) {
            serialNumberQueue.offer(i);
        }
        SER_NO_QUEUE.putIfAbsent(key, serialNumberQueue);
        //从Redis中获取年月日加5位序列数
        return date.concat(produceSerialNumber(key, CommonConstant.NUM_COMMON_5));
    }

    /**
     * 检查并清空指定 key 对应的队列（跨天才清空）
     */
    private void checkAndResetIfNeeded(String key) {
        String today = DateUtil.getCurrDate();
        lastResetDateMap.putIfAbsent(key, new AtomicReference<>(today));
        AtomicReference<String> lastDateRef = lastResetDateMap.get(key);

        String last = lastDateRef.get();
        if (!today.equals(last)) {
            synchronized (getKeyLock(key)) {
                if (!today.equals(lastDateRef.get())) {
                    SER_NO_QUEUE.remove(key);
                    lockMap.remove(key);
                    lastDateRef.set(today);
                    System.out.println("懒清理执行，key=" + key + " 日期切换到: " + today);
                }
            }
        }
    }

    /***
      * @Author xiaodi
      * @Description 生成上传批次号有过期时间
      * @Data 9:16 2023/5/17
      * @Param key
     * @Param length
     * @return java.lang.String
     */
    @Override
    public String produceSerialNumber(final String key, int length) {
        return produceSerialNumber(key, length , false);
    }

    /**
     * 生成序号
     * @param key
     * @param length
     * @param isCirculate 达到预定位数大小时是否从头循环
     * @return
     */
    @Override
    public String produceSerialNumber(final String key, int length, boolean isCirculate) {
        // 跨天时 清除昨日的数据
        checkAndResetIfNeeded(key);

        Queue<Integer> serialNumberQueue = SER_NO_QUEUE.get(key);

        Object keyLock = getKeyLock(key);

        // 初始化队列
        if (serialNumberQueue == null) {
            synchronized (keyLock) {
                serialNumberQueue = initQueue(key);
            }
        }

        // 如果队列中序列值不足，则执行序列值生成
        if (serialNumberQueue.size() < LOW_LEVEL_COUNT) {
            synchronized (keyLock) {
                increase(key, serialNumberQueue, length, isCirculate);
            }
        }
        Integer serialNumber = serialNumberQueue.poll();
        return StringUtils.leftPad(serialNumber.toString(), length, CommonConstant.COMMON_0);
    }

    /***
     * @Author xiaodi
     * @Description 生成上传批次号无过期时间
     * @Data 9:16 2023/5/17
     * @Param key
     * @Param length
     * @return java.lang.String
     */
    @Override
    public String produceSerialNumberNoExpire(String key, int length) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            stringRedisTemplate.opsForValue().increment(key, CommonConstant.NUM_COMMON_1);
        } else {
            stringRedisTemplate.opsForValue().set(key, CommonConstant.COMMON_1);
        }
        String number =  stringRedisTemplate.opsForValue().get(key);
        return StringUtils.leftPad(number,length,CommonConstant.COMMON_0);
    }

    /**
     * 向队列中批量添加序列值
     * @param key
     * @param serialNumberQueue
     * @param length
     * @param isCirculate
     */
    private void increase(final String key, final Queue<Integer> serialNumberQueue, int length, boolean isCirculate) {
        if (serialNumberQueue.size() < LOW_LEVEL_COUNT) {

            String currDate = DateUtil.getCurrDate();
            String bswKey = key + CommonConstant.COLON + currDate;
            // 1. 生成指定数量的序列值
            Long increment = stringRedisTemplate.opsForValue().increment(bswKey, ONCE_FETCH_COUNT);

            // 2. 计算起始与结束的序列值
            int incrIntvalue = increment.intValue();
            int begin = incrIntvalue - ONCE_FETCH_COUNT + CommonConstant.NUM_COMMON_1;
            int end = incrIntvalue;
            String incrementString = increment.toString();

            // 3. 限制序列值的长度
            if (isCirculate) {

                // 3.1 若该批次结束的序列值长度超出指定值，则更新key的起始值为0，同时本批次的结束序列值取对应长度的最大值
                if (incrementString.length() > length) {
                    stringRedisTemplate.opsForValue().set(bswKey, CommonConstant.COMMON_0);
                    end = ((int) Math.pow(CommonConstant.INT_10, length)) - CommonConstant.NUM_COMMON_1;
                }

                // 3.2 若该批次起始的序列值长度超出指定值，则从1开始获取指定容量的序列值
                String batchSizeStr = String.valueOf(ONCE_FETCH_COUNT);
                String beginStr = String.valueOf(begin);
                if (beginStr.length() > length) {
                    stringRedisTemplate.opsForValue().set(bswKey, batchSizeStr);
                    begin = CommonConstant.NUM_COMMON_1;
                    end = ONCE_FETCH_COUNT;
                }
            }

            // 4. 首次执行时，为对应的key设置过期时间
            if (ONCE_FETCH_COUNT == incrIntvalue) {
                stringRedisTemplate.expire(bswKey, TIME_CYCLE, TimeUnit.MILLISECONDS);
            }

            // 5. 将计算出的起始-结束之间的序列值放入队列
            for (int i = begin; i <= end; i++) {
                serialNumberQueue.offer(i);
            }
        }
    }

    /**
     * 初始化队列
     * @param key
     */
    private Queue<Integer> initQueue(String key){
        Queue<Integer> ids = SER_NO_QUEUE.get(key);
        if (ids == null){
            ids = new LinkedBlockingQueue<Integer>(ONCE_FETCH_COUNT + LOW_LEVEL_COUNT);
            SER_NO_QUEUE.put(key, ids);
        }
        return ids;
    }

    /**
     *  获取或创建锁对象
     * @param key
     * @return
     */
    public Object getKeyLock(String key) {
        // 获取或创建锁对象
        return lockMap.computeIfAbsent(key, k -> new Object ());
    }
}
