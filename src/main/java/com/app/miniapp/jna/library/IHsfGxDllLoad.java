package com.app.miniapp.jna.library;

import com.sun.jna.Library;
import com.sun.jna.Native;
import org.springframework.stereotype.Component;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/25
 * <p>UPDATE DATE: 2025/7/25
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
public interface IHsfGxDllLoad extends Library {

    IHsfGxDllLoad INSTANCE = Native.load("yh_interface_xyb", IHsfGxDllLoad.class); // 不需要加 .dll

    // 假设 outStr 是输出参数，分配内存后传入
    int gxyb_call(String transNo, String json, byte[] outStr);

}
