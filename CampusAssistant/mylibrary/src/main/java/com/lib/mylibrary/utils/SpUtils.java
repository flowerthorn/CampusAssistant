package com.lib.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public static <T> void putList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        getEditor().clear();
        getEditor().putString(tag, strJson).apply();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public static <T> List<T> getList(String tag) {
        List<T> datalist=new ArrayList<>();
        String strJson = getSharedPreferences().getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }

    /**
     * 存储Map集合
     * @param key 键
     * @param map 存储的集合
     * @param <K> 指定Map的键
     * @param <T> 指定Map的值
     */

    public static <K,T> void putMap(String key , Map<K,T> map){
        if (map == null || map.isEmpty() || map.size() < 1){
            return;
        }

        Gson gson = new Gson();
        String strJson  = gson.toJson(map);
        getEditor().clear();
        getEditor().putString(key ,strJson);
        getEditor().commit();
    }

    /**
     * 获取Map集合
     * */
    public static <K,T> Map<K,T> getMap(String key){
        Map<K,T> map = new HashMap<>();
        String strJson = getSharedPreferences().getString(key,null);
        if (strJson == null){
            return map;
        }
        Gson gson = new Gson();
        map = gson.fromJson(strJson,new TypeToken<Map<K,T> >(){}.getType());
        return map;
    }
}
