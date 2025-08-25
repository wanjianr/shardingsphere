package com.app.miniapp.redis.service;

/**
 *
 * PURPOSE:
 * DESCRIPTION: 上传批次号序列生成接口
 * CALLED BY:
 * CREATE DATE: 2023年3月29日
 * UPDATE DATE:
 * HISTORY:
 * @author fansen
 * @version 1.0
 * @since java 1.8
 */
public interface INoRuleService {

     public String getBatchNo(String key, String date);
    /**
     * 生成序列号
     * @param key
     * @return
     */
     String produceSerialNumber(String key,int len);

     /**
      * 生成序列号 可设置从头开始
      * @param key
      * @param length
      * @param isCirculate 达到预定位数大小时是否从头循环
      * @return
      */
     String produceSerialNumber(String key, int length , boolean isCirculate);

     /**
      * 生成序列号 无过期时间
      * @param key
      * @param length
      * @return
      */
     String produceSerialNumberNoExpire(String key, int length);

}
