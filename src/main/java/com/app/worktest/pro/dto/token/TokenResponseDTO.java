package com.app.worktest.pro.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token获取响应DTO
 * <p>
 * 用于封装Token认证接口的响应数据，
 * 包含标准的状态码、消息和数据字段。
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
public class TokenResponseDTO {

    /**
     * 响应状态码
     * 0-成功
     */
    @JsonProperty("code")
    private Integer code;

    /**
     * 响应类型
     */
    @JsonProperty("type")
    private String type;

    /**
     * 响应消息
     */
    @JsonProperty("message")
    private String message;

    /**
     * 响应数据（Token字符串）
     */
    @JsonProperty("data")
    private String data;

    /**
     * 判断响应是否成功
     *
     * @return true-成功
     */
    public boolean isSuccess() {
        return code != null && code == 0;
    }
}
