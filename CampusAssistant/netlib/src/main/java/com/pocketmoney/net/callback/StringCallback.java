package com.pocketmoney.net.callback;

import com.pocketmoney.net.frame.IResponse;

/**
 * Created by wangkang on 17/3/27.
 */

public abstract class StringCallback extends NetCallback<String> {
    @Override
    public String handleResponseInBackground(IResponse response) {
        return response.string();
    }
}
