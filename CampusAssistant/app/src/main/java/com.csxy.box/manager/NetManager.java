package com.csxy.box.manager;

import android.content.Context;

import com.csxy.box.bean.BookObj;
import com.csxy.box.constant.UrlConstant;
import com.csxy.box.net.ApiRequest;
import com.csxy.box.net.callback.BaseJsonCallback;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class NetManager {
    public static final String TAG = "__NetManager";

    public static void searchBook(final Context context, BaseJsonCallback<BookObj> callback,String bookKey){
        ApiRequest.get(context, UrlConstant.SEARCH_BOOK+bookKey,callback);
    }
}
