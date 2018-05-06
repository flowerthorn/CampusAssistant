package com.lib.mylibrary.utils;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project : PocketMoney
 * Package : com.app.pocketmoney.Utility
 * Created by zhangziqi on 12/11/14.
 */
public class NumberUtils {




    public static String doubleToString(double d) {
        String i = DecimalFormat.getInstance().format(d);
        return i.replaceAll(",", "");
    }




    public static Integer randomNumber(int min, int max) {
        return min + (int)(Math.random()*(max + min - 1));
    }



    /**
     *
     * @param number
     * @param place
     * @return 将double类型字符串转化保留后place位,
     * 并进行四舍五入(example: decimalRound( 2.999 ,2) >>  3.00)
     *
     */
    public static String decimalRound(double number, int place) {
        return String.format(Locale.ENGLISH, "%." + place + "f", number);
    }

    /**
     *
     * @param number
     * @param place
     * @return @return 将double类型字符串转化保留后place位,
     * 并不进行四舍五入(example: decimalCut( 2.999 ,1) >>  2.9)
     */
    public static String decimalCut(float number, int place) {
        float pow = (float) Math.pow(10, place);
        int i  = (int) (number * ( pow));
        return decimalRound(i / pow, place);
    }

    public static String roundTo1Decimal(double d) {
        return decimalRound(d, 1);
    }

    public static String roundTo2Decimal(double d) {
        return decimalRound(d,2);
    }


    /**
     * @param str   字符串转成展示数据
     * @param place 保留的位数
     * @return 四舍五入保留place位的小数
     * 如果不是数字则不进行处理
     */
    public static String decimalRound(String str, int place) {
        if (isFigure(str)) {
            double number = Double.parseDouble(str);
            return decimalRound(number, place);
        } else {
            return str;
        }
    }


    /**
     * @param str   字符串转成展示数据
     * @param place 保留的位数
     * @return 截取保留place位的小数，并不进行四舍五入
     * 如果不是数字则不进行处理
     */
    public static String decimalCut(String str, int place) {
        if (isFigure(str)) {
            float number = (float) Double.parseDouble(str);
            return decimalCut(number, place);
        } else {
            return str;
        }
    }




    /**
     * @param str
     * @param place        保留位数
     * @param needTimes100 是否需要将所得str*100再运算，如果str的最大为1 则需*100再运算
     * @return 将str转成百分
     * 如果不是数字则不进行处理
     */
    public static String getPercent(String str, int place, boolean needTimes100) {
        if (isFigure(str)) {
            double number = Double.parseDouble(str);
            if (needTimes100) {
                number *= 100;
            }
            return decimalRound(number, place) + "%";
        } else {
            return str;
        }
    }






    public static boolean isDecimals(String str) {
        String regex = "[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    private static boolean isMatch(String regex, String str) {
        if (str == null || str.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static boolean isFigure(String str) {
        if (isInteger(str) || isDecimals(str)) {
            return true;
        }
        return false;
    }

    public static boolean isInteger(String str) {
        return isMatch("[+-]{0,1}0", str) || isMatch("^\\+{0,1}[1-9]\\d*", str) || isMatch("^-[1-9]\\d*", str);
    }
}
