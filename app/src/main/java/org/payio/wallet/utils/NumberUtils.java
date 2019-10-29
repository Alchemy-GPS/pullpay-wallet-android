package org.payio.wallet.utils;

import java.math.BigDecimal;
import java.util.Locale;

public class NumberUtils {
    /**
     * 判断是否是两位小数
     *
     * @param amount 数字
     * @return true or false
     */
    public static boolean isTwoDecimal(String amount) {
        if (amount.contains(".")) {//是小数
            if (amount.length() - (amount.indexOf(".") + 1) == 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是八位小数
     *
     * @param amount 数字
     * @return true or false
     */
    public static boolean isEightDecimal(String amount) {
        if (amount.contains(".")) {//是小数
            if (amount.length() - (amount.indexOf(".") + 1) == 8) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是小数
     *
     * @param amount 数字
     * @return true or false
     */
    public static boolean isDecimal(String amount) {
        return amount.contains(".");
    }

    /**
     * 判断是否大于0
     *
     * @param amount 数字
     * @return true or false
     */
    public static boolean isGreaterThanZero(String amount) {
        double result = Double.parseDouble(amount);
        return result > 0;
    }

    /**
     * 将double数字转换为两位小数的
     * DecimalFormat is a concrete subclass of NumberFormat that formats decimal numbers.
     *
     * @param d
     * @return
     */
    public static String formatDouble2(double d) {

        return String.format(Locale.ENGLISH, "%.2f", d);
    }

    /**
     * 将double数字转换为8位小数的
     * DecimalFormat is a concrete subclass of NumberFormat that formats decimal numbers.
     *
     * @param d
     * @return
     */
    public static String formatDouble8(double d) {

        return String.format(Locale.ENGLISH, "%.8f", d);
    }

    public static String doubleToString(double d) {
//        BigDecimal bigDecimal = new BigDecimal("123456789.123456789");
        BigDecimal bigDecimal = BigDecimal.valueOf(d);
        return bigDecimal.toString();
    }
}
