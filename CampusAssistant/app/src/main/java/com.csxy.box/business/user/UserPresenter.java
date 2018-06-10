package com.csxy.box.business.user;

import com.csxy.box.business.other.AboutActivity;
import com.csxy.box.business.other.FeedBackActivity;
import com.csxy.box.business.h5.WebViewActivty;
import com.csxy.box.constant.UrlConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihongxin on 2018/5/29.
 */
public class UserPresenter implements UserContract.Presenter {

    private UserContract.View mView;

    public UserPresenter(UserContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void gotoScener() {
        Map<String,String> extraHeaders=new HashMap<>();
        extraHeaders.put("X-XSS-Protection","1; mode=block");
        WebViewActivty.actionShow(mView.getContext(),"城院全景", UrlConstant.SCENER,extraHeaders);
    }

    @Override
    public void gotoFeedBack() {
        FeedBackActivity.actionShow(mView.getContext());
    }

    @Override
    public void gotoGuess() {
        WebViewActivty.actionShow(mView.getContext(),"猜你想问","file:///android_asset/help.html",null);
    }

    @Override
    public void gotoAboutUs() {
        AboutActivity.actionShow(mView.getContext());
    }
}
