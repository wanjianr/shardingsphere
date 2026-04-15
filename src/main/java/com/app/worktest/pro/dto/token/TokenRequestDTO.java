package com.app.worktest.pro.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token获取请求DTO
 * <p>
 * 用于封装获取Token接口的请求参数，
 * 对应医保数据共享平台的Token认证接口。
 * </p>
 *
 * @author MiniMax Agent
 * @version 1.0
 * @since 2026-03-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDTO {

    /**
     * 参保地编码
     * 6位行政区划代码
     */
    @JsonProperty("admdvs")
    private String admdvs;

    /**
     * 本人证件类型
     * 01-身份证
     */
    @JsonProperty("psnCertType")
    private String psnCertType;

    /**
     * 本人证件号码
     */
    @JsonProperty("certno")
    private String certno;

    /**
     * 本人姓名
     */
    @JsonProperty("psnName")
    private String psnName;

    /**
     * 本人手机号码
     */
    @JsonProperty("mob")
    private String mob;

    /**
     * 代办人证件类型
     * 01-身份证
     */
    @JsonProperty("agentPsnCertType")
    private String agentPsnCertType;

    /**
     * 代办人证件号码
     */
    @JsonProperty("agentCertno")
    private String agentCertno;

    /**
     * 代办人姓名
     */
    @JsonProperty("agentPsnName")
    private String agentPsnName;

    /**
     * 代办人手机号码
     */
    @JsonProperty("agentMob")
    private String agentMob;

    /**
     * 成年人标志
     * true-成年人
     */
    @JsonProperty("adultFlag")
    private Boolean adultFlag;

    /**
     * 渠道编码
     */
    @JsonProperty("channel")
    private String channel;

    /**
     * 理赔类型
     * 0-医保
     */
    @JsonProperty("claimType")
    private String claimType;
}
