package com.pocketmoney.net.okhttp;


import com.pocketmoney.net.frame.IResponse;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by wangkang on 17/3/20.
 */

public class OkIResponse extends IResponse<Response> {

    public OkIResponse(Response response) {
        super(response);
    }

    @Override
    public String header(String name) {
        return response.header(name);
    }


    @Override
    public Long contentLength() {
        return response.body().contentLength();
    }

    @Override
    public InputStream byteStream() {
        return response.body().byteStream();

    }

    @Override
    public String string() {
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int code() {
        return response.code();
    }

    @Override
    public String url() {
        return response.request().url().toString();
    }


}
