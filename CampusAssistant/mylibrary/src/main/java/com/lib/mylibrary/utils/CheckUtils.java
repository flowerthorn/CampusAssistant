package com.lib.mylibrary.utils;


import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangkang on 17/3/31.
 */

public class CheckUtils {

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }
    public static boolean isEmpty(String str) {
        return str ==null || str.length()==0;
    }
    public static boolean isEmpty(Set set){
        return set == null || set.isEmpty();
    }

    /**
     *
     * @param list
     * @return 如果list为空则返回0 否则返回list的size值
     */
    public static int listSize(List list) {
        return list == null ? 0 : list.size();
    }


    public static boolean isSameList(List<String> list1, List<String> list2) {
        if (list1 == list2) {
            return true;
        }
        if (list1.size() ==list2.size()) {
            for (int i = 0; i < list1.size(); i++) {
                if (!list1.get(i).equals(list2.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isCorrectJsonObject(String json) {
        return !isEmpty(json) && json.startsWith("{")
                && json.endsWith("}");
    }
    public static boolean isCorrectJsonArray(String json) {
        return !isEmpty(json) && json.startsWith("[")
                && json.endsWith("]");
    }

    public static boolean isSimilarList(List list1, List list2) {
        if (list1 == null || list2 == null) {
            return list1 == list2;
        } else {
            if (list1.size() == list2.size()) {
                for (int i = 0; i < list1.size(); i++) {
                    if (list1.get(i) == null || list2.get(i) == null) {
                        if (list1 == list2) {
                            continue;
                        } else {
                            return false;
                        }
                    }
                    if (!list1.get(i).toString().equals(list2.get(i).toString())) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }


    public static boolean isPhoneNumber(String phoneNumber) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isNumber(String number) {
        String regex = "^[0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(number);
        return m.matches();
    }

}
