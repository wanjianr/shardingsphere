package com.app.worktest.pro.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token验证请求DTO
 * <p>
 * 用于checkToken接口，验证用户Token并获取用户信息。
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
public class CheckTokenRequestDTO {

    /**
     * 用户Token
     */
    @JsonProperty("token")
    private String token;
}
