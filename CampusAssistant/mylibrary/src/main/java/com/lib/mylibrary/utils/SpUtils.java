package com.lib.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;


public class SpUtils {

    private static String defaultSpName;
    private static Context applicationContext;


    public static void init(String defaultSpName, Context applicationContext) {
        SpUtils.defaultSpName = defaultSpName;
        SpUtils.applicationContext = applicationContext;
    }

    public static String get(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public static void put(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    public static String get(String key, String defaultValue, String spName) {
        return getSharedPreferences(spName).getString(key, defaultValue);
    }

    public static void put(String key, String value, String spName) {
        getEditor(spName).putString(key, value).apply();
    }
    public static void put(String key, Set<String> value, String spName) {
        getEditor(spName).putStringSet(key, value).apply();
    }


    public static boolean get(String key, boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public static void put(String key, boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    public static void putSynchronously(String key, boolean value) {
        getEditor().putBoolean(key, value).commit();
    }

    public static void put(String key, float value) {
        getEditor().putFloat(key, value).apply();
    }

    public static void put(String key, Set<String> value) {
        getEditor().putStringSet(key, value).apply();
    }

    public static int get(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public static void put(String key, int value) {
        getEditor().putInt(key, value).apply();
    }

    public static long get(String key, long defaultValue) {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    public static float get(String key, float defaultValue) {
        return getSharedPreferences().getFloat(key, defaultValue);
    }

    public static Set<String> getSet(String key, Set<String> set) {
        return getSharedPreferences().getStringSet(key, set);
    }

    public static void put(String key, long value) {
        getEditor().putLong(key, value).apply();
    }

    public static void remove(String key) {
        getEditor().remove(key).apply();
    }

    // 立即写入磁盘
    public static void removeSynchronously(String key) {
        getEditor().remove(key).commit();
    }

    public static void removeSharedPreference(String spName) {
        getSharedPreferences(spName).edit().clear().commit();
    }
    public static void removeDefaultSharedPreference() {
        getSharedPreferences().edit().clear().commit();
    }

    public static boolean contains(String key) {
        return getSharedPreferences().contains(key);
    }

    private static SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }
    private static SharedPreferences.Editor getEditor(String spName) {
        return getSharedPreferences(spName).edit();
    }


    private static SharedPreferences getSharedPreferences() {
        return getSharedPreferences(defaultSpName);
    }

    private static SharedPreferences getSharedPreferences(String spName) {
        if (getContext() == null) {
            throw new IllegalArgumentException("Call the init(defaultSpName,applicationContext) method before operate sharedPreference");
        }
        return getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }



    private static Context getContext() {
        return applicationContext;
    }
}
