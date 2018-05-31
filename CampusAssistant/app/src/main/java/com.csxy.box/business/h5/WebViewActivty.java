package com.csxy.box.business.h5;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.utils.L;
import com.lib.mylibrary.utils.CheckUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lihongxin on 2018/5/27.
 * 简单封装后H5页面
 */
public class WebViewActivty extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.error_layout)
    View errorView;
    @BindView(R.id.repeat_request_btn)
    TextView repeatRequest;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.content_layout)
    RelativeLayout rlWebViewContent;
    private boolean isNetError = false;
    private String url;
    private String title;
    private Map<String, String> extraHeaders = new HashMap<>();

    public static void actionShow(Context context, String title, String url, Map<String, String> extraHeaders) {
        Intent intent = new Intent(context, WebViewActivty.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("extraHeaders", (Serializable) extraHeaders);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        extraHeaders = (Map<String, String>) getIntent().getSerializableExtra("extraHeaders");
        tvTitle.setText(title);
        initWebViewSettings();

        if (extraHeaders == null || extraHeaders.size() == 0) {
            mWebView.loadUrl(url);
        } else {
            L.d("mangguo", "tag webview");
            mWebView.loadUrl(url, extraHeaders);
        }


    }

    private void initWebViewSettings() {
        mWebView.setVerticalScrollBarEnabled(false); //垂直滚动条不显示
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);//屏幕适配:设置webview推荐使用的窗口，设置为true
        webSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式，也设置为true
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);//是否支持缩放
        webSettings.setBuiltInZoomControls(true);//添加对js功能的支持
        webSettings.setDomStorageEnabled(true);  // 存储(storage)
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //webview cache默认不使用缓存
        webSettings.setAppCacheEnabled(false);//H5 CACHE


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    isNetError = true;
                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                L.d("WV", "onPageFinished");
                if (isNetError) {
                    errorView.setVisibility(View.VISIBLE);
                } else {
                    rlWebViewContent.setVisibility(View.VISIBLE);
                    errorView.setVisibility(View.GONE);
                }
                isNetError = false;
                if (title.equals("四六级查询")) {
                    //去除广告
                    String js = getClearAdDivJs();
                    mWebView.loadUrl(js);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    if (title.contains("网页无法打开") || title.contains(url) || title.contains("404") || title.contains("500") || title.contains("Error"))
                        isNetError = true;
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                L.d("WV", "onProgressChanged");
//                if (title.equals("四六级查询")) {
//                    //去除广告
//                    String js = getClearAdDivJs();
//                    mWebView.loadUrl(js);
//                }


            }
        });
        repeatRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });
    }

    private String getClearAdDivJs() {
        //getElementById()	返回对拥有指定 id 的第一个对象的引用。
        //getElementsByName()	返回带有指定名称的对象集合。
        //getElementsByTagName()	返回带有指定标签名的对象集合。
        //getElementsByClassName   返回指定class的对象的集合。
        String js = "javascript:" + "(function(){ ";
        js += "var adDiv" + "= document.getElementsByClassName('" + "wx-cet-ad" + "')[0];";
        js += "if(adDiv!=null) adDiv.style.display=\"none\";";
//        js += "if(adDiv!=null) adDiv.parentNode.removeChild(adDiv);";
        js += "})()";
        L.d("WV", js);
        return js;
    }

    protected void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        synCookies();
    }

    /**
     * 清除webview中的cookie
     */
    public void synCookies() {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
    }

}
