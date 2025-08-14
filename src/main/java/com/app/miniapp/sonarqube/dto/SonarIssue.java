package com.app.miniapp.sonarqube.dto;

import lombok.Data;

import java.io.Serializable;

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
public class SonarIssue implements Serializable {
    private String rule;
    private String severity;
    private String component;
    private String project;
    private String status;
    private String message;
    private String author;
    private String type;
    private String scope;
    private String issueStatus;
}
