package com.csxy.box.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.csxy.box.utils.L;


public class AppGlobal {

    /**
     * 获得应用包名
     *
     * @return
     */
    public static String getPackageName() {
        String packageName = getContext().getPackageName();
        L.d("package::" + packageName);
        return packageName;
    }

    /**
     * 获得应用名
     */
    public static String getAppName() {
        ApplicationInfo applicationInfo = getContext().getApplicationInfo();
        return getContext().getString(applicationInfo.labelRes);
    }

    /**
     * 获得版本号
     *
     * @return appVersion
     */
    public static String getVersionName() {
        try {
            return getContext()
                    .getPackageManager()
                    .getPackageInfo(getContext().getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Got an error";
        }
    }

    /**
     * 获得版本号（没有小数点）
     *
     * @return appVersion
     */
    public static String getVersionCode() {
        try {
            return "" + getContext()
                    .getPackageManager()
                    .getPackageInfo(getContext().getPackageName(), 0)
                    .versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Got an error";
        }
    }

    private static Context getContext() {
        return MyApplication.getInstance();
    }


    /**
     * Get App Source
     * 获得App渠道号
     *
     * @return appSource
     */
    public static String getAppSource() {
        try {
            return getContext().getPackageManager()
                    .getApplicationInfo(getContext().getPackageName(), PackageManager.GET_META_DATA)
                    .metaData.getString("TD_CHANNEL_ID");
        } catch (Exception e) {
            e.printStackTrace();
            return "Got an error";
        }
    }
}
