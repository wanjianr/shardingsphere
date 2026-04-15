package com.app.worktest.pro.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 授权确认请求DTO
 * <p>
 * 用于提交用户的授权确认信息，包含授权场景列表和结算信息列表。
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
public class ConfirmRequestDTO {

    /**
     * 授权参与DTO
     */
    @JsonProperty("authJoinDTO")
    private AuthJoinDTO authJoinDTO;

    /**
     * 结算信息列表
     */
    @JsonProperty("setlList")
    private List<SetlInfoDTO> setlList;

    /**
     * 授权参与DTO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthJoinDTO {

        /**
         * 使用截止时间
         * 格式：yyyy.MM.dd
         */
        @JsonProperty("usedEndTime")
        private String usedEndTime;

        /**
         * 数据范围开始时间
         * 格式：yyyy.MM.dd
         */
        @JsonProperty("dataScpBegnTime")
        private String dataScpBegnTime;

        /**
         * 数据范围结束时间
         * 格式：yyyy.MM.dd
         */
        @JsonProperty("dataScpEndTime")
        private String dataScpEndTime;

        /**
         * 参保统筹区编码
         */
        @JsonProperty("insuAdmdvs")
        private String insuAdmdvs;

        /**
         * 授权场景DTO列表
         */
        @JsonProperty("authScenDDTOList")
        private List<AuthScenDTO> authScenDDTOList;
    }

    /**
     * 授权场景DTO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthScenDTO {

        /**
         * 场景ID
         */
        @JsonProperty("scenId")
        private String scenId;
    }

    /**
     * 结算信息DTO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetlInfoDTO {

        /**
         * 结算时间
         */
        @JsonProperty("setlTime")
        private String setlTime;

        /**
         * 结算ID
         */
        @JsonProperty("setlId")
        private String setlId;
    }
}
