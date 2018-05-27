package com.csxy.box.business.h5;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.base.BaseActivity;

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
    private boolean isNetError = false;
    private String url;
    private String title;

    public static void actionShow(Context context,String title, String url) {
        Intent intent = new Intent(context, WebViewActivty.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        url=getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        tvTitle.setText(title);
        initWebViewSettings();
        mWebView.loadUrl(url);

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
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient() {
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
                if (isNetError) {
                    errorView.setVisibility(View.VISIBLE);
                } else {
                    mWebView.setVisibility(View.VISIBLE);
                    errorView.setVisibility(View.GONE);
                }
                isNetError = false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    if (title.contains("网页无法打开")||title.contains(url)||title.contains("404") || title.contains("500") || title.contains("Error"))
                        isNetError=true;
                }
            }
        });
        repeatRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });
    }

    protected void setTitle(String title) {
        tvTitle.setText(title);
    }

}
