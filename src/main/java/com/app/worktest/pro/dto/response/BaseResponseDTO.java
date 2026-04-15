package com.app.worktest.pro.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用响应DTO基类
 * <p>
 * 所有API响应的基础结构，包含状态码、消息和数据字段。
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
public class BaseResponseDTO<T> {

    @JsonProperty("type")
    private String type;

    /**
     * 响应状态码
     */
    @JsonProperty("code")
    private Integer code;

    /**
     * 响应消息
     */
    @JsonProperty("message")
    private String message;

    /**
     * 响应数据（可能是加密字符串或解密后的对象）
     */
    @JsonProperty("data")
    private T data;

    /**
     * 判断响应是否成功
     *
     * @return true-成功
     */
    public boolean isSuccess() {
        return code != null && code == 0;
    }
}
