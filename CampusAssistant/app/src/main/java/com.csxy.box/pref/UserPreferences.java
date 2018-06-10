package com.csxy.box.pref;

import com.lib.mylibrary.utils.SpUtils;

/**
 * Created by lihongxin on 2018/6/11.
 */
public class UserPreferences {

    public static boolean getLoginStatus() {
        return SpUtils.get("loginStatus", false);
    }

    public static void setLoginStatus(boolean isLogin) {
        SpUtils.put("loginStatus", isLogin);
    }

    public static void setUserName(String name) {
        SpUtils.put("username", name);
    }

    public static String getUserName() {
        return SpUtils.get("username", "游客");
    }

    public static void setId(String id) {
        SpUtils.put("id", id);
    }

    public static String getId() {
        return SpUtils.get("id", "无");
    }


}
