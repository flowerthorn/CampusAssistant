package com.pocketmoney.net.frame;


import com.pocketmoney.net.callback.NetCallback;
import com.pocketmoney.net.okhttp.OkIClient;

import java.util.Map;


/**
 * Created by wangkang on 17/3/20.
 */

public abstract class IClient {

    public static IClient getIClient(IClientBuilder builder) {
        return new OkIClient(builder);
    }



    protected IClient(IClientBuilder builder) {
    }

    public abstract IRequest get(Object tag, String url, NetCallback netCallback);

    public abstract IRequest post(Object tag, String url, Map<String, String> params, NetCallback netCallback);

    public abstract void cancelRequest(Object tag);

}
