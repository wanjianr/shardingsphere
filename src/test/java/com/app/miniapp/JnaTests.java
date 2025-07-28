package com.app.miniapp;

import com.app.miniapp.jna.library.IHsfGxDllLoad;
import com.app.miniapp.jna.service.HsfDllService;
import com.app.miniapp.shardingdemo.dto.TicketJoinFile;
import com.app.miniapp.shardingdemo.entity.*;
import com.app.miniapp.shardingdemo.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.apache.shardingsphere.infra.util.json.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/15
 * <p>UPDATE DATE: 2025/7/15
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@SpringBootTest
public class JnaTests {

    @Autowired
    private HsfDllService hsfDllService;

    @Test
    void testHsfDllService() throws Exception {
        // 调用 HsfDllService 的方法
        String result = hsfDllService.businessHandle("Hello from JNA");

        // 输出结果
        System.out.println("Result from HsfDllService: " + result);
    }

//    public static void main(String[] args) {
//        String transNo = "1401";
//        String json = "{\"transNo\":\"1401\",\"transType\":\"passwordCheck\",\"timestamp\":\"20210427121812\",\"data\":{\" fixmedinsCode \": \"1020001\",\"psnNo\": \"1111111\"}}";
//
//        // 为输出参数分配缓冲区
//        byte[] outputBuffer = new byte[1024]; // 假设返回内容不会超过 1024 字节
//
//        int result = IHsfGxDllLoad.INSTANCE.gxyb_call(transNo, json, outputBuffer);
//
//        if (result == 0) {
//            String response = new String(outputBuffer).trim();
//            System.out.println("调用成功，返回：" + response);
//        } else {
//            System.out.println("调用失败，错误码：" + result);
//        }
//    }

    public static void main(String[] args) {
        try {
            // 创建 COM 对象
            ActiveXComponent yinhaiObject = new ActiveXComponent("YinHai.XJ.Actual.Interface.New");

            // 要传的参数
            String transNo = "1401";
            String jsonData = "{\"transNo\":\"1401\",\"transType\":\"passwordCheck\",\"timestamp\":\"20210427121812\",\"data\":{\" fixmedinsCode \": \"1020001\",\"psnNo\": \"1111111\"}}";

            // 第三个参数是 byref 输出参数
            Variant jylsh = new Variant(""); // 先初始化为空字符串

            // 调用方法：gxyb_call(transNo, json, ref jylsh)
            Dispatch.call(yinhaiObject, "gxyb_call", transNo, jsonData, jylsh);

            // 获取返回值
            System.out.println("交易流水号（返回）: " + jylsh.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用失败，请检查 COM 注册或线路！");
        }
    }

//    public static void main(String[] args) {
//        // 初始化 COM 线程
//        com.jacob.com.ComThread.InitSTA();
//
//        try {
//            // 创建 COM 对象，类似于 PB 的 ConnectToNewObject
//            ActiveXComponent yinhaiObject = new ActiveXComponent("YinHai.XJ.Actual.Interface.New");
//            long result = 0; // 假设 ConnectToNewObject 无返回值或成功为 0
//
//            // 检查连接是否成功
//            if (yinhaiObject.getObject() == null) {
//                System.out.println("错误: 请检查线路!");
//                return;
//            }
//
//            // 构造 JSON 输入字符串
//            String jsonInput = "{\"transNo\":\"1401\",\"transType\":\"passwordCheck\",\"timestamp\":\"20210427121812\",\"data\":{\"fixmedinsCode\":\"1020001\",\"psnNo\":\"1111111\"}}";
//            String transNo = "1401";
//
//            // 调用 gxyb_call 方法
//            // 使用 Variant 包装参数，第三个参数为输出参数（类似 PB 的 ref）
//            Variant vTransNo = new Variant(transNo);
//            Variant vJsonInput = new Variant(jsonInput);
//            Variant vTransSeq = new Variant("", true); // true 表示 byRef
//
//            // 调用 COM 方法
//            Variant callResult = Dispatch.call(yinhaiObject, "gxyb_call", vTransNo, vJsonInput, vTransSeq);
//
//            // 检查调用结果
//            if (callResult.getInt() == 0) {
//                System.out.println("调用成功，交易流水号: " + vTransSeq.getString());
//            } else {
//                System.out.println("调用失败，返回码: " + callResult.getInt());
//            }
//
//            // 断开 COM 对象连接，类似于 PB 的 DisconnectObject
//            yinhaiObject.invoke("DisconnectObject");
//        } catch (Exception e) {
//            System.out.println("错误: " + e.getMessage());
//        } finally {
//            // 释放 COM 线程
//            com.jacob.com.ComThread.Release();
//        }
//    }
}
