package com.app.miniapp.feign.controller;

import com.app.miniapp.feign.dto.RegisterResult;
import com.app.miniapp.feign.dto.StandardInput;
import com.app.miniapp.feign.dto.StandardOutput;
import com.app.miniapp.feign.service.FeignService;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


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
@RestController
@RequestMapping("/bsw")
public class FeignController {

    @Autowired
    private FeignService feignService;

    @PostMapping("/signIn")
    public RegisterResult signIn(@RequestBody StandardInput input) throws Exception {
        System.out.println("Received signIn input:");
        return feignService.signIn(input);
    }

    @PostMapping("/callServiceA")
    public StandardOutput callService1(@RequestBody StandardInput input) {
        System.out.println("Received callService1 input:");
        return feignService.callServiceA(input);
    }

    @PostMapping("/callServiceA1")
    public StandardOutput callService2(@RequestBody StandardInput input) {
        System.out.println("Received callService2 input:");
        return feignService.callServiceA1(input);
    }


    @PostMapping("/callServiceB")
    public String callService3(@RequestBody StandardInput input) {
        System.out.println("Received callService3 input:");
        return feignService.callServiceB(input);
    }

    @PostMapping("/callServiceBD")
    public byte[] callService5(@RequestBody StandardInput input) {
        System.out.println("Received callService5 input:");
        byte[] bytes = feignService.callServiceDown(input);
        saveByteArrayToZipFile(bytes, "response.zip"); // 保存字节数组到文件
        return bytes;
    }

    public void saveByteArrayToZipFile(byte[] bytes, String fileName) {
        // 获取当前用户的主目录
        String userHome = System.getProperty("user.dir");
        String filePath = userHome + File.separator + fileName; // 拼接文件路径

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(bytes); // 将字节数组写入文件
            fos.flush(); // 确保所有数据写入
            System.out.println("文件已保存到: " + filePath);
        } catch (IOException e) {
            e.printStackTrace(); // 打印异常信息
        }
    }

    @PostMapping("/callServiceC")
    public String callService4(@RequestBody StandardInput input) {
        System.out.println("Received callService4 input:");
        return feignService.callServiceC(input);
    }

    @RequestMapping(value = "/Bevpbank.do", method = RequestMethod.POST)
    public @ResponseBody Object Bevpbank(@RequestBody BankDocInputDTO req) {
        // todo
        System.out.println("Received Bevpbank input:");
        return null;
    }
}
