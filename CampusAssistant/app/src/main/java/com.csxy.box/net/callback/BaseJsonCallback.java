package com.csxy.box.net.callback;

import com.csxy.box.utils.L;
import com.google.gson.Gson;
import com.lib.mylibrary.utils.CheckUtils;
import com.pocketmoney.net.callback.NetCallback;
import com.pocketmoney.net.frame.IResponse;

/**
 * Created by lihongxin on 2018/5/21.
 */
public abstract class BaseJsonCallback<T> extends NetCallback<T> {
    private static final String TAG = "__JSON";
    private Class<T> clz;

    public BaseJsonCallback(Class<T> clz) {
        this.clz = clz;
    }

    @Override
    public T handleResponseInBackground(IResponse response) {
        String string = response.string();


        if (clz == null) {
            return null;
        }
        if (!CheckUtils.isCorrectJsonObject(string)) {
            return null;
        }
        L.d(TAG, response.url() + "\n" + mContext.getClass().getSimpleName() + "__" + "responseData: " + string);
        return getEntity(clz, string);
    }

    private T getEntity(Class<T> clz, String originData) {
        Gson gson = new Gson();
        if (clz == String.class) {
            return (T) originData;

        } else {
            T object = gson.fromJson(originData, clz);
            return object;
        }
    }

    @Override
    public abstract void onResponse(String url, T response, int statusCode);

    @Override
    public abstract void onFailure(String url, int statusCode);

    //其他说明 如果服务端代码规范 可在这里做统一的错误处理 error ，token过期处理，异常处理
    //需要和服务端统一规范 BaseObj
}
