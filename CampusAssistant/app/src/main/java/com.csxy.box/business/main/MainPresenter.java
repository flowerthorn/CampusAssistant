package com.csxy.box.business.main;

/**
 * Created by lihongxin on 2018/5/6.
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mMainView;

    public MainPresenter(MainContract.View mainView) {
        mMainView = mainView;
        mainView.setPresenter(this);

    }
}
