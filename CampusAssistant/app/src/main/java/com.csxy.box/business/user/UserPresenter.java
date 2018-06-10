package com.csxy.box.business.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.csxy.box.business.other.AboutActivity;
import com.csxy.box.business.other.FeedBackActivity;
import com.csxy.box.business.h5.WebViewActivty;
import com.csxy.box.business.other.LoginActivity;
import com.csxy.box.constant.UrlConstant;
import com.csxy.box.pref.UserPreferences;
import com.csxy.box.utils.ToastUtils;
import com.lib.mylibrary.utils.SpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihongxin on 2018/5/29.
 */
public class UserPresenter implements UserContract.Presenter {

    private UserContract.View mView;
    private LoginReceiver mReceive;

    public UserPresenter(UserContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void gotoScener() {
        Map<String, String> extraHeaders = new HashMap<>();
        extraHeaders.put("X-XSS-Protection", "1; mode=block");
        WebViewActivty.actionShow(mView.getContext(), "城院全景", UrlConstant.SCENER, extraHeaders);
    }

    @Override
    public void gotoFeedBack() {
        FeedBackActivity.actionShow(mView.getContext());
    }

    @Override
    public void gotoGuess() {
        WebViewActivty.actionShow(mView.getContext(), "猜你想问", "file:///android_asset/help.html", null);
    }

    @Override
    public void gotoAboutUs() {
        AboutActivity.actionShow(mView.getContext());
    }

    @Override
    public void checkLogin() {
        if (UserPreferences.getLoginStatus()) {
            mView.showLoginView();
        } else {
            mView.showNoLoginView();
        }
    }

    @Override
    public void gotoExit() {

        UserPreferences.setLoginStatus(false);
        UserPreferences.setUserName("游客");
        UserPreferences.setId("学号:无");
        mView.showNoLoginView();
    }

    @Override
    public void gotoLogin() {
        LoginActivity.actionShow(mView.getContext());
    }

    @Override
    public void registerLoginReceiver() {
        mReceive = new LoginReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LoginActivity.login.success");
        mView.getContext().registerReceiver(mReceive, intentFilter);

    }

    @Override
    public void onDestroy() {
        mView.getContext().unregisterReceiver(mReceive);
    }

    class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mView.showLoginView();
        }
    }
}
