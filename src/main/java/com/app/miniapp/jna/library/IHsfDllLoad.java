package com.app.miniapp.jna.library;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.springframework.stereotype.Component;

/**
 * <p>PURPOSE: 动态库加载
 * <p>DESCRIPTION:
 * <p>CALLED BY:
 * <p>CREATE DATE: 2023-06-29
 * <p>UPDATE DATE:
 * <p>UPDATE USER:
 * <p>HISTORY:
 * @version 1.0
 * @author wanjian
 * @since java 1.8
 * @see
 */
@Component
public interface IHsfDllLoad extends Library {

    IHsfDllLoad INSTANCE = Native.load("SiInterface_hsf1", IHsfDllLoad.class);

    /**
     * 初始化函数,检查整个运行环境，并清除本地临时文件
     * @param pErrMsg
     * @return
     */
    int INIT(String pErrMsg);

    /**
     * 交易函数：HIS 系统开发商需要向医保中心发送业务请求的通用函数
     * @param inputData
     * @param outputData
     * @return
     */
    int BUSINESS_HANDLE(String inputData, Pointer outputData);
}
