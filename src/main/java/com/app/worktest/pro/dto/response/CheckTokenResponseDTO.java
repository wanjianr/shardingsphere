package com.app.worktest.pro.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token验证响应DTO
 * <p>
 * 包含验证结果和用户基本信息。
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
public class CheckTokenResponseDTO {

    /**
     * 被调查人姓名
     */
    @JsonProperty("psnName")
    private String psnName;

    /**
     * 被调查人证件号码
     */
    @JsonProperty("investigatedCertno")
    private String investigatedCertno;

    /**
     * 被调查人证件类型
     */
    @JsonProperty("investigatedCertType")
    private String investigatedCertType;
}
