package com.csxy.box.business.eat;

/**
 * Created by lihongxin on 2018/5/31.
 */
public class EatPresenter implements EatContract.Presenter {
    private EatContract.View mView;
    public EatPresenter(EatContract.View view){
        mView=view;
        mView.setPresenter(this);
    }

    @Override
    public void gotoMyEatCollect() {
        MealsActivity.actionShow(mView.getContext(),"my_meals","","");
    }
}
