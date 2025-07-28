package com.app.miniapp.jna.service;

import com.app.miniapp.jna.library.IHsfDllLoad;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * <p>PURPOSE:
 * <p>DESCRIPTION: 调用动态库j接口实现
 * <p>DESCRIPTION:
 * <p>CALLED BY:	wanjian
 * <p>CREATE DATE: 2023年6月30日
 * <p>UPDATE DATE: 2023年6月30日
 * <p>UPDATE USER:
 * <p>HISTORY:		1.0
 * @version 1.0
 * @author wanjian
 * @since java 1.8.0
 * @see
 */
@Service
public class HsfDllService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HsfDllService.class);

    private static final ReentrantLock LOCK = new ReentrantLock();

    public static final Integer NUM_COMMON_0 = 0;

    private static boolean isInitialized = false;

    private static Pointer outputPointer;

    @Value("${ctj.lockTimeout:30}")
    private int lockTimeout;

    /**
     * 动态库初始化，只需执行一次
     */
    private static void init() {
        if (!isInitialized) {
            exeInit();
        }
    }

    private synchronized static void exeInit() {
        if (!isInitialized) {
            // 设置jan调用编码格式
            System.setProperty("jna.encoding", "GBK");
            String pErrMsg = "";
            int init = IHsfDllLoad.INSTANCE.INIT(pErrMsg);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("动态库初始化 调用状态:{} 错误信息:{}", init, pErrMsg);
            }
            if (!NUM_COMMON_0.equals(init)) {
                System.out.println("动态库初始化失败，调用状态: " + init + ", 错误信息: " + pErrMsg);
            }
            isInitialized = true;
        }
    }

    /**
     * 请求动态库交易函数
     * @param input
     * @return
     */
    public String businessHandle(String input) throws Exception {
        // 动态库初始化
        init();
        try {
            if (LOCK.tryLock(lockTimeout, TimeUnit.SECONDS)) {
                return business(input);
            }
            LOGGER.error("请求动态库接口超时。");
        } finally {
            // 如果当前线程持有锁则释放
            if (LOCK.isHeldByCurrentThread()) {
                LOCK.unlock();
            }
        }
        return null;
    }

    public String business(String input) {
        outputPointer = new Memory(512*512);
        try {
            int code = IHsfDllLoad.INSTANCE.BUSINESS_HANDLE(input, outputPointer);
            String output = outputPointer.getString(0, "GBK");
            if (!NUM_COMMON_0.equals(code)) {
                LOGGER.error("动态库交易函数调用失败 调用状态:{} 输入:{} 输出:{}", code, input, output);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("动态库 交易函数调用状态:{}", code);
            }
            return output;
        } finally {
            if (outputPointer != null) {
                long peer = Pointer.nativeValue(outputPointer);
                // 手动释放内存
                Native.free(peer);
                Pointer.nativeValue(outputPointer, NUM_COMMON_0);
            }
        }
    }
}
