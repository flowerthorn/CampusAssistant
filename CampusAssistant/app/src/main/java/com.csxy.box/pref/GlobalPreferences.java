package com.csxy.box.pref;

import com.lib.mylibrary.utils.SpUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongxin on 2018/5/22.
 */
public class GlobalPreferences {

    /**
     *
     * sp存储最近图书搜索历史
     */

    public static void setRecentSearchBooks(ArrayList<String> books) {
        SpUtils.putList("recentSearchBook", books);
    }

    public static List<String> getRecentSearchBooks() {
        return SpUtils.getList("recentSearchBook");
    }

    public static void removeRecentSearchBooks() {
        SpUtils.remove("recentSearchBook");
    }
}
