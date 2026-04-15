package com.app.worktest.pro.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 事件追踪保存请求DTO
 * <p>
 * 用于记录用户在H5页面的操作行为。
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
public class SaveEventTrackRequestDTO {

    /**
     * 操作行为编码
     * 400-授权确认
     */
    @JsonProperty("oprtBhvr")
    private String oprtBhvr;

    /**
     * 操作标志
     * 1-开始
     * 2-完成
     */
    @JsonProperty("oprtFlag")
    private String oprtFlag;
}
