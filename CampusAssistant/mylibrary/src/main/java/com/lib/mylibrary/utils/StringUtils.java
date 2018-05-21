package com.lib.mylibrary.utils;


import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project : PocketMoney
 * Package : com.app.pocketmoney.Utility
 * Created by zhangziqi on 1/7/15.
 */
public class StringUtils {

    /**
     * 去掉 appName 的 中文或者英文括号以及之后的部分
     * @return
     */
    public static String getAppName(String target) {

        if (target.contains("（")) {
            return target.split("（")[0];
        }

        if (target.contains("(")) {
            return target.split("\\(")[0];
        }

        return target;
    }



    public static String convertDecimal(Float f) {
        return new DecimalFormat("0.00").format(f);
    }

    public static String convertDecimal(Double f) {
        return new DecimalFormat("0.00").format(f);
    }

    public static boolean isNumeric(String checkStr) {
        try {
            Integer.parseInt(checkStr);
            return true; // Did not throw, must be a number
        } catch (NumberFormatException err) {
            return false; // Threw, So is not a number
        }
    }

    public static boolean isQQNumber(String account) {
        if (account.length() >= 6) {
            try {
                //noinspection ResultOfMethodCallIgnored
                Long.parseLong(account);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isMobileNumber(String account) {
        Pattern p = Pattern.compile("\\d{11}$");
        Matcher m = p.matcher(account);
        return m.matches();
    }

    public static boolean isEmail(String account) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(account);
        return m.matches();
    }

    public static boolean isIDNumber(String id) {

        // 总长度为18
        if (id.length() != 18) {
            return false;
        }

        // 第7、8位为19或20
        if (!id.substring(6,8).equals("19") && !id.substring(6,8).equals("20")) {
            return false;
        }

        // 第11位只能为0或1
        if (!id.substring(10,11).equals("0") && !id.substring(10,11).equals("1")) {
            return false;
        }

        // 第13、14位为01~31
        try {
            int num = Integer.valueOf(id.substring(12, 14));
            if (num < 1 || num > 31) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
