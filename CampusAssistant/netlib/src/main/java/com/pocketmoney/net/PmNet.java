package com.pocketmoney.net;

import android.content.Context;

/**
 * Created by chengjinran on 2017/11/28.
 */

public class PmNet {
    public static Context sContext;

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context sContext) {
        PmNet.sContext = sContext;
    }
}
