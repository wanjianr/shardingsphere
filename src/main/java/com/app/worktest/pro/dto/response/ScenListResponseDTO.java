package com.app.worktest.pro.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 场景列表响应DTO
 * <p>
 * 包含可授权的场景信息列表。
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
public class ScenListResponseDTO {

    /**
     * 场景信息列表
     */
    @JsonProperty("list")
    private List<ScenItemDTO> list;

    /**
     * 场景项DTO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScenItemDTO {

        /**
         * 场景ID
         */
        @JsonProperty("scenId")
        private String scenId;

        /**
         * 场景名称
         */
        @JsonProperty("scenName")
        private String scenName;

        /**
         * 场景描述
         */
        @JsonProperty("scenDesc")
        private String scenDesc;
    }
}
