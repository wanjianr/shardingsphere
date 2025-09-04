package com.app.miniapp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/28
 * <p>UPDATE DATE: 2025/8/28
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
public class DateTest {

    public static String getYearWeek(String dateStr) {
        // Parse the input date string (assuming YYYY-MM-DD or YYYYMMDD)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(dateStr, formatter); // e.g., "20250827"

        // Get year and ISO week number
        int year = date.getYear();
        WeekFields weekFields = WeekFields.ISO;
        int week = date.get(weekFields.weekOfWeekBasedYear());

        // Format as YYYY-WW
        return String.format("%d-%02d", year, week);
    }

    public static void main(String[] args) {
//        LocalDate date = LocalDate.of(2025, 1, 1);
//
//        // 定义格式：周历年份 + W + 两位周序号
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYww");
//
//        String formatted = date.format(formatter);
//
//        System.out.println("日期: " + date);
//        System.out.println("所在周: " + formatted);


//        List<String> list = Arrays.asList("20250101", "20250102", "20250103", "20250104", "20250105", "20250106",
//                "20250107", "20250108", "20250109", "20250110", "20250111", "20250112", "20250113",
//                "20250114", "20250115", "20250116", "20250117", "20250118", "20250119", "20250120",
//                "20250121", "20250122", "20250123", "20250124", "20250125", "20250126", "20250127",
//                "20250128", "20250129", "20230130", "20250131");

        List<String> list = Arrays.asList("202501","202502","202601", "202412");
        System.out.println(Collections.min(list));
    }
}
