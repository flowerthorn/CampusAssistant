package com.csxy.box.net;

import android.content.Context;

import com.lib.mylibrary.utils.MapUtils;
import com.pocketmoney.net.callback.FileCallBack;
import com.pocketmoney.net.callback.NetCallback;
import com.pocketmoney.net.callback.StringCallback;
import com.pocketmoney.net.cookie.MemoryCookieStore;
import com.pocketmoney.net.frame.IClient;
import com.pocketmoney.net.frame.IClientBuilder;
import com.pocketmoney.net.frame.IRequest;

import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ApiRequest {
    private static IClient iClient;
    private static ThreadPoolExecutor encryptThreadPool;


    static {
        IClientBuilder builder = new IClientBuilder();
        builder.setTimeOut(20, TimeUnit.SECONDS);
        builder.setCookieSotre(new MemoryCookieStore());
        iClient = IClient.getIClient(builder);
        encryptThreadPool = new ThreadPoolExecutor(0, 6, 60L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());

    }


    public static IRequest stringGet(Context context, String url, StringCallback callback) {
        return get(context, url, callback);
    }

    public static IRequest stringPost(Context context, String url, StringCallback callback,String... args) {
        return post(context, url, callback,args);
    }


    public static IRequest fileGet(Context context, String url, FileCallBack callback) {
        return get(context, url, callback);
    }


    public static IRequest post(Context context, String url, NetCallback netCallback, final String... args) {
        netCallback.setContext(context);
        Map<String, String> params= MapUtils.getOperationParams(args);
        return iClient.post(context, url, params, netCallback);
    }


    public static IRequest get(Context context, String url, NetCallback netCallback) {
        netCallback.setContext(context);
        return iClient.get(context, url, netCallback);

    }

    public static void cancelRequest(Context context) {
        iClient.cancelRequest(context);
    }

}
