package com.app.miniapp.feign.service;

import com.app.miniapp.feign.client.FeignClientA;
import com.app.miniapp.feign.client.FeignClientA1;
import com.app.miniapp.feign.client.FeignClientB;
import com.app.miniapp.feign.client.FeignClientC;
import com.app.miniapp.feign.dto.*;
import com.app.miniapp.feign.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/30
 * <p>UPDATE DATE: 2025/7/30
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Service
public class FeignService {

    @Value("${service.url}")
    private String SERVICE_URL;
//    private static final String SERVICE_URL = "http://172.16.101.48:8080/ebc/api/medins/qury/queryUpldRslt";
//    private static final String SERVICE_URL = "http://59.211.232.34:10007/mbs-hiIntf-fangchenggang/web/api/fsi/callService";

    @Resource
    private FeignClientA feignClientA;

    @Resource
    private FeignClientA1 feignClientA1;

    @Resource
    private FeignClientB feignClientB;

    @Resource
    private FeignClientC feignClientC;

    @Autowired
    private StandardInputConvert standardInputConvert;


    public StandardOutput callServiceA(StandardInput input) {
        StandardOutput standardOutput = feignClientA.callService(URI.create(SERVICE_URL), input);
        return standardOutput;
    }

    public StandardOutput callServiceA1(StandardInput input) {
        return feignClientA1.callService(URI.create(SERVICE_URL), input);
    }

    public String callServiceB(StandardInput input) {
        return feignClientB.callService(URI.create(SERVICE_URL), input);
    }

    public byte[] callServiceDown(StandardInput input) {
        return feignClientB.callServiceDown(URI.create(SERVICE_URL), input);
    }

    public String callServiceC(StandardInput input) {
        byte[] bytes = feignClientC.callService(URI.create(SERVICE_URL), input);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public RegisterResult signIn(StandardInput standardInput) throws Exception {
        StandardInput registerInput = standardInputConvert.standardInputToRegisterInput(standardInput);
        registerInput = standardInputConvert.convertStandardInput(registerInput);

        RegisterRequest req = MacUtil.macAndIp();
        req.setOpter_no("fms001");
        registerInput.setInput(new SignIn(req));
        System.out.println("9001请求：" + JsonUtils.objectToJson(registerInput));
        String signRes = feignClientB.callService(URI.create(SERVICE_URL), registerInput);
        if (StringUtils.isEmpty(signRes)) {
            System.out.println("获取签到信息失败");
            return null;
        }
        StandardOutput standardOutput = JsonUtils.jsonToObject(signRes, StandardOutput.class);
        String output = JsonUtils.objectToJson(standardOutput.getOutput());
        System.out.println("9001响应：" + output);
        SignInOutb signInOutb = JsonUtils.jsonToObject(output, SignInOutb.class);
        System.out.println("9001响应实体：" + JsonUtils.objectToJson(signInOutb));
        return signInOutb.getSigninoutb();
    }
}
