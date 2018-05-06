package com.pocketmoney.net.frame;

import com.pocketmoney.net.cookie.CookieStore;

import java.util.concurrent.TimeUnit;


/**
 * Created by wangkang on 17/4/28.
 */

public class IClientBuilder {
    private long timeout = 20;

    private TimeUnit timeoutTimeUnit = TimeUnit.SECONDS;
    private CookieStore cookieSotre;


    public void setTimeOut(long timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeoutTimeUnit = timeUnit;
    }

    public void setCookieSotre(CookieStore cookieSotre) {
        this.cookieSotre = cookieSotre;
    }

    public CookieStore getCookieSotre() {
        return cookieSotre;
    }

    public long getTimeout() {
        return timeout;
    }

    public TimeUnit getTimeoutTimeUnit() {
        return timeoutTimeUnit;
    }
}
