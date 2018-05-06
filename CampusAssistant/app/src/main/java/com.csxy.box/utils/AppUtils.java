package com.csxy.box.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.csxy.box.app.MyApplication;

import java.util.List;

/**
 * Created by lihongxin on 2018/5/5.
 */
public class AppUtils {

    public static boolean activityIsFinishing(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return (activity.isDestroyed());
        }
        return (activity.isFinishing());

    }

    public static boolean activityIsFinishing(Context context) {
        boolean finishing = false;
        if (context instanceof Activity) {
            finishing = activityIsFinishing(((Activity) context));
        }
        return finishing;
    }

    public static boolean isForeground(Context context) {
        if (context != null) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();

            for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
                if (processInfo.processName.equals(context.getPackageName())) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //去app设置界面
    public static void  toAppDetailSettingActivity(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);

    }

    public static void openApp(Context context, String target) {
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(target));
    }
    public static boolean isInstallApp(String packageName) {
        return MyApplication.getContext().getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }
}
