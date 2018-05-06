package com.pocketmoney.net.okhttp;



import com.pocketmoney.net.cookie.CookieStore;
import com.pocketmoney.net.utils.Exceptions;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by zhy on 16/3/10.
 */
public class CookieJarImpl implements CookieJar
{
    private CookieStore cookieStore;
    public static final String TAG = "__cookie";

    public CookieJarImpl(CookieStore cookieStore)
    {
        if (cookieStore == null) Exceptions.illegalArgument("cookieStore can not be null.");
        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies)
    {
        String c = (cookies ==  null || cookies.size()==0)? "empty":cookies.size()+"";
        cookieStore.add(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url)
    {
        List<Cookie> cookies = cookieStore.get(url);
        String c = (cookies ==  null || cookies.size()==0)? "empty":cookies.size()+"";
        return cookies;
    }

    public CookieStore getCookieStore()
    {
        return cookieStore;
    }
}
