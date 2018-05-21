package com.csxy.box.business.function;

import com.csxy.box.business.book.BookActivity;

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

    @Override
    public void gotoDeliver() {

    }

    @Override
    public void gotoWeather() {

    }
}