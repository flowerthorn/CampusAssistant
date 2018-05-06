package com.csxy.box.app;

import android.app.Application;
import android.content.Context;

import com.csxy.box.utils.L;

public class MyApplication extends Application {

    public final static String TAG = "__CSXY.";

    private static MyApplication instance;
    private static Context context;

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.life("Application", "onCreate");
        instance = this;
        context = this;
    }
}
