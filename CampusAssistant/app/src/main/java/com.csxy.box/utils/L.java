package com.csxy.box.utils;

import android.util.Log;
import com.csxy.box.BuildConfig;
import com.csxy.box.app.MyApplication;

public class L {


    private static final String TAG = MyApplication.TAG + "__LOG";

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "" + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "" + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "" + msg);
        }
    }

    public static void life(String nameTag, String method) {
        if (BuildConfig.DEBUG) {
            Log.d("__life_" + nameTag, "onLife:" + method);
        }
    }

    public static void thread(String where) {
        d("(" + where + ") is on " + Thread.currentThread().getName() + "thread ");
    }

}
