package com.app.worktest.pro.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 参保信息响应DTO
 * <p>
 * 包含参保人的参保地信息列表。
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
public class GetInsuInfoResponseDTO {

    /**
     * 参保统筹区信息列表
     */
    @JsonProperty("insuAdmdvsNames")
    private List<InsuAdmdvsDTO> insuAdmdvsNames;

    /**
     * 参保统筹区DTO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InsuAdmdvsDTO {

        /**
         * 统筹区编码
         */
        @JsonProperty("code")
        private String code;

        /**
         * 统筹区名称
         */
        @JsonProperty("name")
        private String name;
    }
}
