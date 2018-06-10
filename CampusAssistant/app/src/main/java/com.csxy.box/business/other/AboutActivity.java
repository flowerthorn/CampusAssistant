package com.csxy.box.business.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.app.AppGlobal;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.business.h5.WebViewActivty;
import com.csxy.box.constant.UrlConstant;

/**
 * Created by lihongxin on 2018/6/11.
 */
public class AboutActivity extends BaseActivity {
    private TextView tvTitle;
    private ImageView ivBack;
    private TextView tvVersion;
    private ImageView ivLauncher;
    private RelativeLayout mGithub;

    public static void actionShow(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tvTitle = findViewById(R.id.tv_title);
        tvVersion=findViewById(R.id.tv_version);
        ivLauncher=findViewById(R.id.iv_launcher);
        mGithub=findViewById(R.id.rl_github);
        ivBack=findViewById(R.id.iv_backTb);
        tvTitle.setText("关于");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvVersion.setText(AppGlobal.getVersionName());
        ivLauncher.setImageResource(R.mipmap.ic_launcher_round);
        mGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivty.actionShow(mContext,"Github", UrlConstant.MY_GITHUB,null);
            }
        });

    }
}
