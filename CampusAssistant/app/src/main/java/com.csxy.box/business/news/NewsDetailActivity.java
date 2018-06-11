package com.csxy.box.business.news;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.bean.NewsItem;
import com.csxy.box.business.h5.WebViewActivty;
import com.csxy.box.net.ApiRequest;
import com.csxy.box.utils.L;
import com.pocketmoney.net.callback.StringCallback;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lihongxin on 2018/6/11.
 */
public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.iv_forward)
    ImageView ivForward;
    @BindView(R.id.news_webView)
    WebView mWebView;
    @BindView(R.id.repeat_request_btn)
    TextView repeatRequest;
    @BindView(R.id.error_layout)
    View errorView;
    @BindView(R.id.content_layout)
    RelativeLayout rlWebViewContent;
    @BindView(R.id.iv_backTb)
    ImageButton ivBack;
    private String finalData;
    private NewsItem newsItem;
    public static void actionShow(Context context, NewsItem  item) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("newsItem", item);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        newsItem= (NewsItem) getIntent().getSerializableExtra("newsItem");
        initWebViewSettings();
        ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "资讯分享");
                intent.putExtra(Intent.EXTRA_TEXT, newsItem.getTitle()+"\n"+newsItem.getUrl());
                startActivity(Intent.createChooser(intent, "来自:"+newsItem.getAuthor_name()));
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadHtml();


    }

    private void loadHtml() {
        ApiRequest.stringGet(mContext, newsItem.getUrl(), new StringCallback() {
            @Override
            public void onResponse(String url, String response, int statusCode) {
                errorView.setVisibility(View.GONE);
                dealHtml(response);
            }

            @Override
            public void onFailure(String url, int statusCode) {
                rlWebViewContent.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void dealHtml(String s) {
        String todeletebottom=s.substring(s.indexOf("<div id=\"news_check\">"));

        finalData=s.replace(todeletebottom,"");
        String append="<script src=\"https://mini.eastday.com/toutiaoh5/js/photoswipe/photoswipe.min.js\"></script>\n" +
                "<script src=\"https://mini.eastday.com/toutiaoh5/js/common.min.js\"></script>\n" +
                "<script src=\"https://mini.eastday.com/toutiaoh5/js/gg_details_v2.min.js\"></script>\n" +
                "</body>\n" +
                "</html>";
        finalData=finalData+append;
        rlWebViewContent.setVisibility(View.VISIBLE);
        mWebView.loadDataWithBaseURL("file:///android_asset/",finalData,"text/html", "utf-8","");

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

        repeatRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadHtml();
            }
        });
    }

}
