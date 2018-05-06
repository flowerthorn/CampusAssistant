package com.pocketmoney.net.frame;

import java.io.InputStream;

/**
 * Created by wangkang on 17/3/20.
 */

public abstract class IResponse<T> {

    protected final T response;

    public IResponse(T response) {
        this.response = response;
    }

    public abstract String header(String name);

    public abstract Long contentLength();

    public abstract InputStream byteStream();

    public abstract String string();

    public abstract int code();

    public abstract String url() ;

}
