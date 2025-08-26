package com.app.miniapp.jasper.util;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/26
 * <p>UPDATE DATE: 2025/8/26
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
public class Utility {
    private static final String[] DIGITS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] UNITS = {"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};

    public static String toChineseCapital(double num) {
        if (num == 0) return "零圆整";
        String sign = num < 0 ? "负" : "";
        num = Math.abs(num);
        String strNum = String.format("%.2f", num).replace(".", "");
        while (strNum.length() < 14) strNum = "0" + strNum; // Pad to handle up to billions
        String intPart = strNum.substring(0, strNum.length() - 2);
        String decPart = strNum.substring(strNum.length() - 2);
        String intCn = getIntCn(intPart);
        String decCn = getDecCn(decPart);
        String unit = "圆";
        if (decCn.isEmpty()) unit = "圆整";
        return sign + intCn + unit + decCn;
    }

    private static String getIntCn(String intNum) {
        StringBuilder sb = new StringBuilder();
        boolean zeroFlag = false;
        for (int i = 0; i < intNum.length(); i++) {
            int digit = intNum.charAt(i) - '0';
            int index = intNum.length() - 1 - i;
            if (digit == 0) {
                zeroFlag = true;
            } else {
                if (zeroFlag) {
                    sb.append(DIGITS[0]);
                    zeroFlag = false;
                }
                sb.append(DIGITS[digit]).append(UNITS[index % 12]);
            }
            if ((index % 4 == 0) && zeroFlag) zeroFlag = false; // Reset for unit groups
        }
        return sb.toString().replaceAll("零+", "零").replaceAll("零$", "");
    }

    private static String getDecCn(String decNum) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < decNum.length(); i++) {
            int digit = decNum.charAt(i) - '0';
            if (digit != 0) {
                String unit = (i == 0) ? "角" : "分";
                sb.append(DIGITS[digit]).append(unit);
            }
        }
        return sb.toString();
    }
}
