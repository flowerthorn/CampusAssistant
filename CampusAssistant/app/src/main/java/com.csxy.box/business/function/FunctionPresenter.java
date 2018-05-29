package com.csxy.box.business.function;

import com.csxy.box.business.book.BookActivity;
import com.csxy.box.business.h5.WebViewActivty;
import com.csxy.box.business.weather.WeatherActivity;
import com.csxy.box.constant.UrlConstant;
import com.csxy.box.net.ApiRequest;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class FunctionPresenter implements FunctionContract.Presenter {
    private FunctionContract.View mFunctionView;

    public FunctionPresenter(FunctionContract.View mFunctionView) {
        this.mFunctionView = mFunctionView;
        mFunctionView.setPresenter(this);

    }

    @Override
    public void gotoCourse() {
    }

    @Override
    public void gotoBook() {
        BookActivity.actionShow(mFunctionView.getContext());
    }

    @Override
    public void gotoGrade() {

    }

    @Override
    public void gotoStudyRoom() {

    }

    @Override
    public void gotoLevel() {

    }

    @Override
    public void gotoEat() {

    }
    //快递查询
    @Override
    public void gotoDeliver() {
        WebViewActivty.actionShow(mFunctionView.getContext(),"快递查询", UrlConstant.DELIVER_QUERY,null);

    }

    @Override
    public void gotoWeather() {
        WeatherActivity.actionShow(mFunctionView.getContext());
    }
}
