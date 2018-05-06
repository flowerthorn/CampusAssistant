package com.pocketmoney.net.frame;

import android.os.Handler;
import android.os.Looper;

import com.pocketmoney.net.callback.NetCallback;

import java.io.IOException;


/**
 * Created by wangkang on 17/4/28.
 */

public class ResponseHandler {

    public static final int FAILURE_NET = -1;
    public static final Handler uiHandler = new Handler(Looper.getMainLooper());

    public static void executeSuccessCallback(final String url, final IResponse response, final NetCallback netCallback) {
        if (netCallback != null) {
            final int code = response.code();
            //如果200执行成功回调 非200执行失败回调
            if (code == 200) {
                final Object responseResource = netCallback.handleResponseInBackground(response);
                if (!netCallback.getInterruptFlag()) {
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            netCallback.onResponse(url, responseResource, code);
                        }
                    });
                }
            } else {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        netCallback.onFailure(url, code);
                    }
                });
            }
        }
    }

    public static void executeFailureCallback(final String url, final IOException e, final NetCallback netCallback) {
        netCallback.onError(url, e);
        if (netCallback != null) {
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    netCallback.onFailure(url, FAILURE_NET);
                }
            });
        }
    }

}
