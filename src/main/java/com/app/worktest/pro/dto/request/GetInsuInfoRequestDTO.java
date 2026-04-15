package com.app.worktest.pro.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参保信息查询请求DTO
 * <p>
 * 用于根据证件信息查询参保人的参保信息。
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
public class GetInsuInfoRequestDTO {

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
