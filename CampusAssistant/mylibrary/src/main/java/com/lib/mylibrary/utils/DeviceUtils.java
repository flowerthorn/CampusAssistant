package com.lib.mylibrary.utils;

/**
 * Created by lihongxin on 2018/5/5.
 */
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class DeviceUtils {

    public static boolean isMiui() {
        return Build.MANUFACTURER.contains("Xiaomi");
    }

    public static boolean isMiuiV5() {
        String line = "";
        BufferedReader reader = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name");
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return "V5".equals(line);
    }

    // 当内存小于200M的时候主动kill后台进程返回true,反则返回false不做任何操作
    public static boolean isKillBackgroundProcesses(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        if (mi.availMem / (1024 * 1024) < 200) {
            List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
            String packageName = context.getPackageName();
            if (infoList != null) {
                for (int i = 0; i < infoList.size(); ++i) {
                    ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
                    if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        String[] pkgList = appProcessInfo.pkgList;
                        for (int j = 0; j < pkgList.length; ++j) {
                            if (!packageName.equals(pkgList[j])) {
                                am.killBackgroundProcesses(pkgList[j]);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
