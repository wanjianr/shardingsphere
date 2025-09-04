package com.app.miniapp.java.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期工具类
 * 提供获取近期周日期和月份的方法
 */
public class DateUtils {


    private static final DateTimeFormatter DATE_FORMAT_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DATE_FORMAT_YYYYMM = DateTimeFormatter.ofPattern("yyyyMM");

    /**
     * 方法1：获取近dispCnt周的周日日期（含本周），升序排列
     */
    public static List<String> getRecentWeeks(Integer dispCnt) {
        List<String> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // 找到本周周日（ISO定义：周一第一天，周日最后一天）
        LocalDate thisSunday = today.with(DayOfWeek.SUNDAY);

        for (int i = dispCnt - 1; i >= 0; i--) {
            LocalDate sunday = thisSunday.minusWeeks(i);
            result.add(sunday.format(DATE_FORMAT_YYYYMMDD));
        }

        return result;
    }

    /**
     * 方法2：获取近dispCnt月（含本月），升序排列
     */
    public static List<String> getRecentMonths(Integer dispCnt) {
        List<String> result = new ArrayList<>();
        YearMonth thisMonth = YearMonth.now();

        for (int i = dispCnt - 1; i >= 0; i--) {
            YearMonth ym = thisMonth.minusMonths(i);
            result.add(ym.format(DATE_FORMAT_YYYYMM));
        }

        return result;
    }

    /**
     * 方法4：获取近dispCnt周中，第一周（最早那周）的周一日期
     */
    public static String getFirstWeekMonday(Integer dispCnt) {
        LocalDate today = LocalDate.now();
        // 本周周一
        LocalDate thisMonday = today.with(DayOfWeek.MONDAY);
        // 最早那一周的周一
        LocalDate firstMonday = thisMonday.minusWeeks(dispCnt - 1);
        return firstMonday.format(DATE_FORMAT_YYYYMMDD);
    }

    // 测试用 main 方法
    public static void main(String[] args) {
        System.out.println("近5周周日日期: " + getRecentWeeks(2));
        System.out.println("近5个月: " + getRecentMonths(2));
        System.out.println("前5周周一: " + getFirstWeekMonday(2));
    }

}
