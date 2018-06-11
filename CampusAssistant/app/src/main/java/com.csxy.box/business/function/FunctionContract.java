package com.csxy.box.business.function;

import com.csxy.box.base.interfaces.BasePresenter;
import com.csxy.box.base.interfaces.BaseView;

/**
 * Created by lihongxin on 2018/5/21.
 */
public interface FunctionContract {
    interface View extends BaseView<Presenter>{

    }
    interface Presenter extends BasePresenter {
        void gotoBook();//前往图书模块
        void gotoLevel();////前往四六级模块
        void gotoEat();////前往食堂模块
        void gotoDeliver();////前往快递模块
        void gotoWeather();////前往天气模块
    }
}
