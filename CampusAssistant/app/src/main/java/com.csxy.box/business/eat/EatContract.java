package com.csxy.box.business.eat;

import com.csxy.box.base.interfaces.BasePresenter;
import com.csxy.box.base.interfaces.BaseView;

/**
 * Created by lihongxin on 2018/5/31.
 */
public interface EatContract {
    interface Presenter extends BasePresenter {
        void gotoMyEatCollect();//查看我的收藏

    }

    interface View extends BaseView<Presenter> {

    }
}
