package com.app.miniapp.sonarqube.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/14
 * <p>UPDATE DATE: 2025/8/14
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Data
public class SumSonarIssue implements Serializable {
    private String rule;
    private String message;
    private Integer cnt;
    private List<String> components;
}
