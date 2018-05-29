package com.csxy.box.business.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csxy.box.R;
import com.csxy.box.base.BaseTabFragment;
import com.csxy.box.constant.UrlConstant;

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
    public void sceneryOnClick(){
        mPresenter.gotoScener();
    }
    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
