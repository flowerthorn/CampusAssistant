package com.pocketmoney.net.callback;

import android.content.Context;
import android.os.Handler;

import com.pocketmoney.net.frame.IResponse;
import com.pocketmoney.net.frame.ResponseHandler;

import java.io.IOException;
import java.util.Map;

/**
 * Created by wangkang on 17/4/28.
 */

public abstract class NetCallback<T> {

    private boolean mInterruptFlag = false;
    protected static Handler mHandler = ResponseHandler.uiHandler;
    protected Context mContext;

    // 暂存请求参数，便于发生错误时重新发起请求
    protected Map<String, String> mParams;

    // 暂存请求参数，加密请求参数存储在此数组中
    protected String[] mArgs = null;

    public abstract void onResponse(String url, T response, int statusCode);

    public abstract void onFailure(String url, int statusCode);

    public abstract T handleResponseInBackground(IResponse response);

    /**
     * 在 handleResponseInBackground 方法中使用,用于阻止onResponse的回调
     *
     * @param interruptFlag
     */
    protected void setInterruptFlag(boolean interruptFlag) {
        this.mInterruptFlag = interruptFlag;
    }

    public boolean getInterruptFlag() {
        return mInterruptFlag;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void onError(String url, final IOException e) {
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public void setParams(Map<String, String> params) {
        this.mParams = params;
    }

    public String[] getArgs() {
        return mArgs;
    }

    public void setArgs(String[] args) {
        this.mArgs = args;
    }
}
