package com.app.worktest.pro.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 结算列表响应DTO
 * <p>
 * 包含参保人的医保结算信息列表。
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
public class GetSetlListResponseDTO {

    /**
     * 结算信息列表
     */
    @JsonProperty("list")
    private List<SetlItemDTO> list;

    /**
     * 结算项DTO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetlItemDTO {

        /**
         * 结算ID
         */
        @JsonProperty("setlId")
        private String setlId;

        /**
         * 结算时间
         */
        @JsonProperty("setlTime")
        private String setlTime;

        /**
         * 就诊类型
         */
        @JsonProperty("mdtrtType")
        private String mdtrtType;

        /**
         * 就诊类型名称
         */
        @JsonProperty("mdtrtTypeName")
        private String mdtrtTypeName;

        /**
         * 医疗机构编码
         */
        @JsonProperty("medinsCode")
        private String medinsCode;

        /**
         * 医疗机构名称
         */
        @JsonProperty("medinsName")
        private String medinsName;

        /**
         * 结算金额
         */
        @JsonProperty("setlAmount")
        private Double setlAmount;
    }
}
