package com.app.miniapp.feign.dto;

import lombok.Data;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/30
 * <p>UPDATE DATE: 2025/7/30
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Data
public class EbcInput {

    private String fixmedinsCode;
    private String fixmedinsName;
    private String bizStsb;
    private String upldBchno;

}
