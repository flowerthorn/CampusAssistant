package com.lib.mylibrary.cache.libcore.io;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by bubu on 2017/9/18.
 */

public class DiskLruCacheUtils {

    private static final String TAG = "DiskLruCacheUtils";

    private static final String LRU_CACHE_DIR = "DiskLruCache";

    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024;

    private static Context sContext;
    private static DiskLruCacheUtils sInstance;
    public static DiskLruCache sDiskLruCache;

    private DiskLruCacheUtils(Context context) {
        sContext = context;
        initDiskLruCache(sContext);
    }

    public DiskLruCache initDiskLruCache(Context context) {
        File cacheFile = getCacheDir(context, LRU_CACHE_DIR);
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
        try {
            sDiskLruCache = DiskLruCache.open(cacheFile, Utils.getAppVersion(context), 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sDiskLruCache;
    }

    /**
     * 获取缓存路径
     * 有sd卡 /sdcard/Android/data/<application package>/cache 这个路径
     * 无sd卡获取到的是 /data/data/<application package>/cache 这个路径
     *
     * @param context
     * @return
     */
    public File getCacheDir(Context context, String file) {
        String cachepath;
        if (Utils.isSdCardExist() || !Environment.isExternalStorageRemovable()) {
            //  /sdcard/Android/data/<application package>/cache
            cachepath = context.getExternalCacheDir().getPath();

        } else {
            //  /data/data/<application package>/cache
            cachepath = context.getCacheDir().getPath();
        }
        return new File(cachepath + File.separator + file);
    }


    public static DiskLruCacheUtils getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DiskLruCacheUtils.class) {
                if (sInstance == null) {
                    sInstance = new DiskLruCacheUtils(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取缓存大小
     *
     * @return 这个方法会返回当前缓存路径下所有缓存数据的总字节数，以byte为单位，如果应用程序中需要在界面上显示当前缓存数据的总大小，就可以通过调用这个方法计算出来
     */
    public long CacheSize() {
        return sDiskLruCache.size();

    }

    public void put(String key, @NonNull String value) {
        key = Utils.hashKeyForDisk(key);
        DiskLruCache.Editor edit = null;
        BufferedWriter bw = null;
        try {
            edit = editor(key);
            if (edit == null) return;
            OutputStream os = edit.newOutputStream(0);
            bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(value);
            edit.commit();//write CLEAN
        } catch (IOException e) {
            e.printStackTrace();
            try {
                //s
                edit.abort();//write REMOVE
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAsString(String key) {
        key = Utils.hashKeyForDisk(key);
        InputStream inputStream = null;
        try {
            inputStream = get(key);
            if (inputStream == null) return null;
            ByteArrayOutputStream boa = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[2048];
            while ((len = inputStream.read(buffer)) != -1) {
                boa.write(buffer, 0, len);
            }
            inputStream.close();
            boa.close();
            byte[] result = boa.toByteArray();
            return new String(result, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getAsJson(String key) {
        String val = getAsString(key);
        try {
            if (val != null)
                return new JSONObject(val);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getAsJSONArray(String key) {
        String JSONString = getAsString(key);
        try {
            JSONArray obj = new JSONArray(JSONString);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // byte 数据 读写

    /**
     * 保存 byte数据 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的数据
     */
    public void put(String key, byte[] value) {
        OutputStream out = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = editor(key);
            if (editor == null) {
                return;
            }
            out = editor.newOutputStream(0);
            out.write(value);
            out.flush();
            editor.commit();//write CLEAN
        } catch (Exception e) {
            e.printStackTrace();
            try {
                editor.abort();//write REMOVE
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public byte[] getAsBytes(String key) {
        byte[] res = null;
        InputStream is = get(key);
        if (is == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[256];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            res = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    // bitmap 数据 读写
    public void put(String key, Bitmap bitmap) {
        put(key, Utils.bitmap2Bytes(bitmap));
    }

    public Bitmap getAsBitmap(String key) {
        byte[] bytes = getAsBytes(key);
        if (bytes == null) return null;
        return Utils.bytes2Bitmap(bytes);
    }

    // drawable 数据 读写
    public void put(String key, Drawable value) {
        put(key, Utils.drawable2Bitmap(value));
    }

    public Drawable getAsDrawable(String key) {
        byte[] bytes = getAsBytes(key);
        if (bytes == null) {
            return null;
        }
        return Utils.bitmap2Drawable(Utils.bytes2Bitmap(bytes));
    }

    // other methods
    public static boolean remove(String key) {
        try {
            // key = Utils.hashKeyForDisk(key);
            return sDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() throws IOException {
        sDiskLruCache.close();
    }

    public void delete() throws IOException {
        sDiskLruCache.delete();
    }

    public void flush() throws IOException {
        sDiskLruCache.flush();
    }

    public boolean isClosed() {
        return sDiskLruCache.isClosed();
    }

    public long size() {
        return sDiskLruCache.size();
    }

    public File getDirectory() {
        return sDiskLruCache.getDirectory();
    }


    // 遇到文件比较大的，可以直接通过流读写
    //basic editor
    public static DiskLruCache.Editor editor(String key) {
        try {
            //  key = Utils.hashKeyForDisk(key);
            //wirte DIRTY
            DiskLruCache.Editor edit = sDiskLruCache.edit(key);
            //edit maybe null :the entry is editing
            if (edit == null) {
                Log.w(TAG, "the entry spcified key:" + key + " is editing by other . ");
            }
            return edit;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    //basic get
    public static InputStream get(String key) {
        try {
            //DiskLruCache.Snapshot snapshot = sDiskLruCache.get(Utils.hashKeyForDisk(key));
            DiskLruCache.Snapshot snapshot = sDiskLruCache.get(key);
            if (snapshot == null) //not find entry , or entry.readable = false
            {
                Log.e(TAG, "not find entry , or entry.readable = false");
                return null;
            }
            //write READ
            return snapshot.getInputStream(0);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static class Utils {

        public static boolean isSdCardExist() {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }

        public static int getAppVersion(Context context) {
            try {
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                return info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return 1;
        }


        public static String hashKeyForDisk(String key) {
            String cacheKey;
            try {
                final MessageDigest mDigest = MessageDigest.getInstance("MD5");
                mDigest.update(key.getBytes());
                cacheKey = bytesToHexString(mDigest.digest());
            } catch (NoSuchAlgorithmException e) {
                cacheKey = String.valueOf(key.hashCode());
            }
            return cacheKey;
        }

        public static String bytesToHexString(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        }

        public static byte[] bitmap2Bytes(Bitmap bm) {
            if (bm == null) {
                return null;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }

        public static Bitmap bytes2Bitmap(byte[] bytes) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }


        /**
         * Drawable → Bitmap
         */
        public static Bitmap drawable2Bitmap(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            // 取 drawable 的长宽
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            // 取 drawable 的颜色格式
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            // 建立对应 bitmap
            Bitmap bitmap = Bitmap.createBitmap(w, h, config);
            // 建立对应 bitmap 的画布
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            // 把 drawable 内容画到画布中
            drawable.draw(canvas);
            return bitmap;
        }

        /*
             * Bitmap → Drawable
             */
        @SuppressWarnings("deprecation")
        public static Drawable bitmap2Drawable(Bitmap bm) {
            if (bm == null) {
                return null;
            }
            BitmapDrawable bd = new BitmapDrawable(bm);
            bd.setTargetDensity(bm.getDensity());
            return new BitmapDrawable(bm);
        }
    }

}
