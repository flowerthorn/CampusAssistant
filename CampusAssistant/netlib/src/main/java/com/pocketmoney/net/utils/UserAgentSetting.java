package com.pocketmoney.net.utils;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;

import com.pocketmoney.net.PmNet;

/**
 * Created by chenmo on 2017/11/15.
 */

public class UserAgentSetting {
    public static String getUserAgent() {
        String userAgent = "";
        Context context = PmNet.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context != null) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
