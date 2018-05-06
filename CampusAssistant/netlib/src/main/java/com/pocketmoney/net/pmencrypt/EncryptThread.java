package com.pocketmoney.net.pmencrypt;

import android.os.Handler;
import android.os.Looper;

import java.util.Map;

/**
 * Created by wangkang on 17/4/28.
 */

public abstract class EncryptThread extends Thread{

    private static Handler handler = new Handler(Looper.getMainLooper());

    private final String[] args;

    private OnParamsCallback onParamsCallback;


    public EncryptThread(String... args) {
        this.args = args;
    }
    @Override
    public void run() {
        final Map<String, String> params = getParams(args);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (onParamsCallback != null) {
                    onParamsCallback.onParamsDone(params);
                }
            }
        });
    }


    public EncryptThread setOnParamsCallback(OnParamsCallback onParamsCallback) {
        this.onParamsCallback = onParamsCallback;
        return this;
    }

    protected abstract Map<String,String> getParams(String[] ars);


}
