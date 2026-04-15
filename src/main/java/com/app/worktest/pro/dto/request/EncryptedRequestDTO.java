package com.app.worktest.pro.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 加密请求体DTO
 * <p>
 * 用于封装所有需要SM4加密的接口请求参数，
 * 包含随机数、加密参数、时间戳和签名。
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
public class EncryptedRequestDTO {

    /**
     * 随机字符串（32位十六进制），用作SM4-CBC加密的IV
     */
    @JsonProperty("noceStr")
    private String noceStr;

    /**
     * SM4加密后的参数（Base64编码）
     */
    @JsonProperty("param")
    private String param;

    /**
     * 秒级Unix时间戳
     */
    @JsonProperty("timestamp")
    private Long timestamp;

    /**
     * 请求签名（SHA256-Base64）
     */
    @JsonProperty("signature")
    private String signature;
}
