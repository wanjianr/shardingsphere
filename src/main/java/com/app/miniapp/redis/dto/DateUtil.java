package com.app.miniapp.redis.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/22
 * <p>UPDATE DATE: 2025/8/22
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
public class DateUtil {

    public static final String DATE_FORMAT_2="yyyyMMdd";
    /**
     * 取本地日期时间.
     *
     * @return 格式化后的日期时间字符
     */
    public static String getCurrDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_2));
    }
}
