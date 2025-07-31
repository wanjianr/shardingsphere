package com.app.miniapp.feign.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * <p>PURPOSE:
 * <p>DESCRIPTION: 9001 返回实体类
 * <p>DESCRIPTION:
 * <p>CALLED BY:	wanjian
 * <p>CREATE DATE: 2023年7月6日
 * <p>UPDATE DATE: 2023年7月6日
 * <p>UPDATE USER:
 * <p>HISTORY:		1.0
 * @version 1.0
 * @author wanjian
 * @since java 1.8.0
 * @see
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInOutb {

    /**
     * 9001输出-签到信息（节点标识： signinoutb）
     */
    private RegisterResult signinoutb;

    public RegisterResult getSigninoutb() {
        return signinoutb;
    }

    public void setSigninoutb(RegisterResult signinoutb) {
        this.signinoutb = signinoutb;
    }
}
