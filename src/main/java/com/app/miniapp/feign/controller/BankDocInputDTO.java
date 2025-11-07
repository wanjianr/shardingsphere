package com.app.miniapp.feign.controller;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION: 请求银行入参
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/9/24
 * <p>UPDATE DATE: 2025/9/24
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Data
public class BankDocInputDTO implements Serializable {

    private static final long serialVersionUID = 3517555987588408398L;

    /**
     * 签发日期
     */
    private String issueDate;

    /**
     * 付款方银行账号
     */
    private String payterBankAcct;

    /**
     * 银行回单凭证号
     */
    private String bankRectNo;

    /**
     * 页号
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;
}
