package com.csxy.box.business.user;

import com.csxy.box.base.interfaces.BasePresenter;
import com.csxy.box.base.interfaces.BaseView;

/**
 * Created by lihongxin on 2018/5/27.
 */
public interface UserContract {
    interface Presenter extends BasePresenter {
        void gotoScener();//前往城院全景
        void gotoFeedBack();//前往反馈页面
        void gotoGuess();//前往猜你想问页面
        void gotoAboutUs();//前往关于我们页面
    }

    interface View extends BaseView<Presenter> {
    }
}
