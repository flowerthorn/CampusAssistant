package com.pocketmoney.net.frame;

/**
 * Created by wangkang on 17/3/24.
 */

public abstract class IRequest<T> {
    protected  final T t;

    public IRequest(T t) {
        this.t = t;

    }

    public abstract void cancel();

}
