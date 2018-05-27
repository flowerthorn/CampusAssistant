package com.csxy.box.base.interfaces;

import android.content.Context;

/**
 * Created by lihongxin on 2018/5/6.
 */
public interface BaseView<T> {
    Context getContext();

    void setPresenter(T presenter);
}
