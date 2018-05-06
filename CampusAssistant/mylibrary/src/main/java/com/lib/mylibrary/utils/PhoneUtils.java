package com.lib.mylibrary.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Project : PocketMoney
 * Package : com.app.pocketmoney.Global
 * Created by zhangziqi on 11/26/14.
 */
public class PhoneUtils {

    /**
     * Get imei
     * Need Permission  android.permission.READ_PHONE_STATE
     *
     * @return imei
     */
    public static String getImei(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * Get model
     *
     * @return model
     */
    public static String getModel() {
        return String.valueOf(Build.MODEL);
    }

    /**
     * 获得分辨率
     *
     * @return
     */
    public static String getMetrics(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels + "x" + metrics.heightPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    public static int getDisplayHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }


    /**
     * 获得CPU信息
     *
     * @return
     */
    public static String getHardware() {
        return String.valueOf(Build.HARDWARE);
    }

    /**
     * 获得mac地址
     *
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String macAddress = wifiInfo.getMacAddress();
            return macAddress == null ? "NULL" : macAddress;
        } catch (Exception e) {
            return "NULL";
        }
    }

    /**
     * 获得SSID
     *
     * @return
     */
    public static String getSSID(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
            return ssid == null ? "NULL" : ssid;
        } catch (Exception e) {
            return "NULL";
        }
    }

    /**
     * 获得 Api Level
     *
     * @return
     */
    public static String getApiLevel() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    /**
     * 获得 Android 版本号
     *
     * @return
     */
    public static String getAndroidVersion() {
        return String.valueOf(Build.VERSION.RELEASE);
    }

    /**
     * 获得网络类型
     *
     * @return
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        try {
            if (networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return "WIFI";
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return networkInfo.getSubtypeName();
                }
                return "NO_CONNECTION";
            } else {
                return "NO_CONNECTION";
            }
        } catch (Exception e) {
            return "NO_CONNECTION";
        }
    }


    /**
     * Get apps on device
     *
     * @return a arraylist contains apps
     */
    public static ArrayList getLocalApps(Context context) {
        ArrayList<String> apps = new ArrayList<>(20);
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                apps.add(packageInfo.packageName);
            }
        }
        return apps;
    }

    public static boolean installedApp(Context context, String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }


    public static boolean installedQQ(Context context) {
        return installedApp(context, "com.tencent.mobileqq");
    }

    public static boolean installedWechat(Context context) {
        return installedApp(context, "com.tencent.mm");
    }


    /**
     * Check if current device is emulator
     *
     * @return result
     */
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic");
    }

    public static boolean hasSIMCard(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT;
    }


    public static boolean isLocationIsOn(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static String getPhoneSimOperatorChinese(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telManager != null) {
            String operator = telManager.getSimOperator();
            if (operator != null) {
                if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
                    return "中国移动";
                } else if (operator.equals("46001")) {
                    return "中国联通";
                } else if (operator.equals("46003")) {
                    return "中国电信";
                }
            }
            return operator;
        }
        return null;
    }

    public static String getPhoneSimOpeatorEnglish(Context context) {
        String phoneSimOperator = PhoneUtils.getPhoneSimOperatorChinese(context);
        if (!CheckUtils.isEmpty(phoneSimOperator)) {
            if ("中国移动".equals(phoneSimOperator)) {
                return "China Mobile";
            } else if ("中国联通".equals(phoneSimOperator)) {
                return "China Unicom";
            } else if ("中国电信".equals(phoneSimOperator)) {
                return "China Telecom";
            }
        }
        return "No Sim";
    }

    public static String getImesi(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telManager != null) {
            return telManager.getSubscriberId();
        }
        return null;

    }




}
