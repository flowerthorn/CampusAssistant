package com.csxy.box.business.function;

import com.csxy.box.interfaces.BasePresenter;
import com.csxy.box.interfaces.BaseView;

/**
 * Created by lihongxin on 2018/5/21.
 */
public interface FunctionContract {
    interface View extends BaseView<Presenter>{

    }
    interface Presenter extends BasePresenter {
        void gotoCourse();//前往课程表模块
        void gotoBook();//前往图书模块
        void gotoGrade();////前往成绩模块
        void gotoStudyRoom();////前往自习室模块
        void gotoLevel();////前往四六级模块
        void gotoEat();////前往食堂模块
        void gotoDeliver();////前往快递模块
        void gotoWeather();////前往天气模块
    }
}
