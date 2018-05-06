package com.pocketmoney.net.okhttp;


import com.pocketmoney.net.frame.IRequest;

import okhttp3.Call;

/**
 * Created by wangkang on 17/3/24.
 */

public class OkIRequest extends IRequest<Call> {


    public OkIRequest(Call request) {
        super(request);
    }

    @Override
    public void cancel() {
        t.cancel();
    }



}
