package com.app.worktest.pro.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结算信息查询请求DTO
 * <p>
 * 用于查询参保人在指定时间范围内的医保结算信息。
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
public class GetSetlListRequestDTO {

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

    /**
     * 被调查人姓名
     */
    @JsonProperty("psnName")
    private String psnName;

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
     * 就诊类型
     * 空-全部
     */
    @JsonProperty("mdtrtType")
    private String mdtrtType;

    /**
     * 年份
     */
    @JsonProperty("year")
    private String year;
}
