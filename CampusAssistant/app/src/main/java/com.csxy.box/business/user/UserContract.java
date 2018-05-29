package com.csxy.box.business.user;

import com.csxy.box.base.interfaces.BasePresenter;
import com.csxy.box.base.interfaces.BaseView;

/**
 * Created by lihongxin on 2018/5/27.
 */
public interface UserContract {
    interface Presenter extends BasePresenter {
        void gotoScener();//前往城院全景
    }

    interface View extends BaseView<Presenter> {
    }
}
