package com.app.miniapp.feign.controller;

import com.app.miniapp.feign.dto.RegisterResult;
import com.app.miniapp.feign.dto.StandardInput;
import com.app.miniapp.feign.dto.StandardOutput;
import com.app.miniapp.feign.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping("/callServiceC")
    public String callService4(@RequestBody StandardInput input) {
        System.out.println("Received callService4 input:");
        return feignService.callServiceC(input);
    }

}
