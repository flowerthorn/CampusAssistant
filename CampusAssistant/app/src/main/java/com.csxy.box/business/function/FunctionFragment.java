package com.csxy.box.business.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.csxy.box.R;
import com.csxy.box.base.BaseTabFragment;
import com.csxy.box.utils.ToastUtils;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihongxin on 2018/5/6.
 */
public class FunctionFragment extends BaseTabFragment implements FunctionContract.View {

    private FunctionContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_function, container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FunctionPresenter(this);
    }

    @Override
    public void setPresenter(FunctionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 点击课程查询
     */
    @OnClick(R.id.ll_course)
    public void courseOnClick() {
        mPresenter.gotoCourse();
    }

    /**
     * 点击图书查询
     */
    @OnClick(R.id.ll_book)
    public void bookOnclick() {
        mPresenter.gotoBook();
    }

    /**
     * 点击成绩查询
     */
    @OnClick(R.id.ll_grade)
    public void gradeOnClick() {
        mPresenter.gotoGrade();
    }

    /**
     * 点击自习室查询
     */
    @OnClick(R.id.ll_study_room)
    public void stydyRoomOnClick() {
        mPresenter.gotoStudyRoom();
    }

    /**
     * 点击四六级查询
     */
    @OnClick(R.id.ll_level_4_6)
    public void levelOnClick() {
        mPresenter.gotoLevel();
    }

    /**
     * 点击校园食堂
     */
    @OnClick(R.id.ll_eat)
    public void eatOnClick() {
        mPresenter.gotoEat();
    }

    /**
     * 点击我的快递
     */
    @OnClick(R.id.ll_deliver)
    public void deliverOnClick() {
        mPresenter.gotoDeliver();
    }

    /**
     * 点击天气查询
     */
    @OnClick(R.id.ll_weather)
    public void weatherOnClick() {
        mPresenter.gotoWeather();
    }

    /**
     * 点击查看自习室
     */
    @OnClick(R.id.ll_study_room)
    public void roomOnClick() {
        ToastUtils.showLongToast("正在更新该接口，敬请期待");
    }

}
