package com.csxy.box.manager;

import android.text.TextUtils;
import com.csxy.box.app.MyApplication;
import com.csxy.box.utils.L;
import com.google.gson.Gson;
import com.lib.mylibrary.cache.libcore.io.DiskLruCacheUtils;

import java.lang.reflect.Type;

/**
 * Created by chengjinran on 2017/6/20.
 * 管理缓存
 * Caution: 通过get方法获得对象时需要注意处理异常，可能造成始终不能获得正确的类对象而持续崩溃
 */

public class CacheManager {

    private static final String TAG = MyApplication.TAG + CacheManager.class.getSimpleName();

    private static final DiskLruCacheUtils sDiskLruCacheUtils = DiskLruCacheUtils.getInstance(MyApplication.getInstance());
    public static final String FILE_JSON = "json";
    public static final String FILE_WALL_CACHE = "wallCache";
    public static final String FILE_NEWS_CACHE = "newsCache";

    /**
     * 缓存json，默认存储在json.xml中
     *
     * @param key
     * @param json
     */
    public static void put(String key, String json) {
        put(key, json, FILE_JSON);
    }

    /**
     * 获取缓存的json字符串，默认从json.xml中获取
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return get(key, FILE_JSON);
    }

    /**
     * 缓存json，存储在给定文件中
     *
     * @param key
     * @param json
     * @param file
     */
    public static void put(String key, String json, String file) {
        if (json == null) json = "";
        sDiskLruCacheUtils.put(generateKey(key, file), json);
    }

    /**
     * 获取缓存的json字符串，从给定文件中获取
     *
     * @param key
     * @param file
     * @return
     */
    public static String get(String key, String file) {
        return sDiskLruCacheUtils.getAsString(generateKey(key, file));
    }

    /**
     * 获取缓存的数据，转为指定的对象返回，默认从json.xml中获取
     *
     * @param key
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T get(String key, Class<T> clz) {
        return get(key, clz, FILE_JSON);
    }

    /**
     * 获取缓存的数据，转为指定的对象返回，从给定文件中获取
     *
     * @param key
     * @param clz
     * @param file
     * @param <T>
     * @return
     */
    public static <T> T get(String key, Class<T> clz, String file) {
        try {
            String json = get(key, file);

            if (TextUtils.isEmpty(json)) return null;

            if (clz == String.class) {
                return (T) json;
            } else {
                Gson gson = new Gson();
                T object = gson.fromJson(json, clz);
                return object;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取缓存的数据，转为指定的对象返回，从给定文件中获取
     *
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T get(String key, Type type) {
        return get(key, type, FILE_JSON);
    }

    /**
     * 获取缓存的数据，转为指定的对象返回，从给定文件中获取
     *
     * @param key
     * @param type
     * @param file
     * @param <T>
     * @return
     */
    public static <T> T get(String key, Type type, String file) {
        try {
            String json = get(key, file);

            if (TextUtils.isEmpty(json)) return null;

            if (getClass(type) == String.class) {
                return (T) json;
            } else {
                Gson gson = new Gson();
                T object = gson.fromJson(json, type);
                return object;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除缓存，默认文件
     *
     * @param key
     */
    public static void remove(String key) {
        sDiskLruCacheUtils.remove(generateKey(key, FILE_JSON));
    }

    /**
     * 删除缓存，指定文件
     * todo 不好用？
     *
     * @param key
     * @param file
     */
    public static void remove(String key, String file) {
        sDiskLruCacheUtils.remove(generateKey(key, file));
    }

    private static String generateKey(String key, String file) {
        return file + ":" + key;
    }

    private static final String TYPE_NAME_PREFIX = "class ";

    private static String getClassName(Type type) {
        if (type == null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        return className;
    }

    private static Class<?> getClass(Type type) {
        String className = getClassName(type);
        if (className == null || className.isEmpty()) {
            return null;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static String getNetCacheKey(String url, String... args) {
        String key;
        if (args == null || args.length == 0) {
            key = url;
        } else {
            key = url + "?" + args[0] + "=" + args[1];
        }
        L.d(TAG, "getNetCacheKey : key = " + key);
        key = DiskLruCacheUtils.Utils.hashKeyForDisk(key);
        return key;
    }

    public static String getVendorsOrderCacheKey() {
        return "VendorsOrder";
    }

    public static String getMyInfoCacheKey() {
        return "MyInfo";
    }

}
