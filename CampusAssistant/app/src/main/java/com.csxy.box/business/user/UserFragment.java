package com.csxy.box.business.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csxy.box.R;
import com.csxy.box.base.BaseTabFragment;
import com.csxy.box.bean.MealItem;
import com.csxy.box.constant.UrlConstant;
import com.csxy.box.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihongxin on 2018/5/6.
 */
public class UserFragment extends BaseTabFragment implements UserContract.View {
    private UserContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, mRootView);
        new UserPresenter(this);
        return mRootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.ll_scenery)
    public void sceneryOnClick() {
        mPresenter.gotoScener();
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.ll_update)
    public void updateOnClick() {
        ToastUtils.showLongToast("已是最新版本");
        // TODO: 2018/6/11 提供检查更新接口
    }
    @OnClick(R.id.ll_feedback)
    public void feedbackOnClick(){
        mPresenter.gotoFeedBack();
    }
    @OnClick(R.id.ll_guess)
    public void guessOnClick(){
        mPresenter.gotoGuess();
    }
    @OnClick(R.id.ll_about)
    public void aboutOnClick(){
        mPresenter.gotoAboutUs();
    }

}
