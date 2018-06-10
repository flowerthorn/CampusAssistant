package com.csxy.box.manager;

import android.content.Context;

import com.csxy.box.bean.BookObj;
import com.csxy.box.bean.EatObj;
import com.csxy.box.bean.MealObj;
import com.csxy.box.constant.UrlConstant;
import com.csxy.box.net.ApiRequest;
import com.csxy.box.net.callback.BaseJsonCallback;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class NetManager {
    public static final String TAG = "__NetManager";

    //查找图书
    public static void searchBook(final Context context, BaseJsonCallback<BookObj> callback, String bookKey) {
        ApiRequest.get(context, UrlConstant.SEARCH_BOOK + bookKey, callback);
    }

    //查找食堂档口
    public static void searchEat(final Context context, BaseJsonCallback<EatObj> callback) {
        ApiRequest.get(context, UrlConstant.SEARCH_EAT, callback);
    }

    //查看对应档口菜单
    public static void searchMeals(final Context context, BaseJsonCallback<MealObj> callback, String id) {
        ApiRequest.get(context, UrlConstant.SEARCH_MEAL + id, callback);
    }

}
