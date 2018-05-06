package com.csxy.box.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.csxy.box.R;
import com.csxy.box.app.MyApplication;
import com.csxy.box.manager.ActivityManager;
import com.csxy.box.utils.L;
import com.lib.mylibrary.utils.StatusBarUtils;

/**
 * Created by lihongxin on 2018/4/25.
 */

public class BaseActivity extends AppCompatActivity {
    protected final String TAG = MyApplication.TAG + getClass().getSimpleName();
    protected BaseActivity mContext;
    private FrameLayout mBaseFlParent;
    private View mBaseContentView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        L.life(TAG, "onCreate");
        mContext = this;
        ActivityManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        mBaseFlParent = ((FrameLayout) findViewById(R.id.base_fl_parent));
        setStatusbarColor(R.color.white, true);
    }

    protected void setStatusbarColor(int res, boolean isLightBg) {
        StatusBarUtils.setWindowStatusBarColor(mContext, getResources().getColor(res), isLightBg);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        mBaseContentView = inflater.inflate(layoutResID, mBaseFlParent, false);
        mBaseFlParent.addView(mBaseContentView);
    }

    @Override
    protected void onStart() {
        L.life(TAG, "onStart");
        super.onStart();
        //前台后台判断 如果需要
    }

    @Override
    protected void onRestart() {
        L.life(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        L.life(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        L.life(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        L.life(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        L.life(TAG, "onDestroy");
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }
}
