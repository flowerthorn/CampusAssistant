package com.csxy.box.business.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.csxy.box.R;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.base.BaseTabFragment;
import com.csxy.box.business.home.HomeFragment;
import com.csxy.box.business.news.NewsFragment;
import com.csxy.box.business.user.UserFragment;
import com.csxy.box.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {


    private MainContract.Presenter mPresenter;

    @BindView(R.id.rg_tab)
    RadioGroup mRadioGroup;
    @BindView(R.id.rb_home)
    RadioButton mRbFunction;

    private HomeFragment homeFragment;//默认主页
    private NewsFragment newsFragment;//资讯页面
    private UserFragment userFragment;//我的页面
    private BaseTabFragment currentFragment;//当前fragment
    private BaseTabFragment lastCurrentFragment;//上一个选择的fragment

    private long lastBackPressed;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new MainPresenter(this);
        initFragment();
    }

    private void initFragment() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchFragment(checkedId);
            }
        });
        userFragment = new UserFragment();
        homeFragment = new HomeFragment();
        newsFragment = new NewsFragment();
        lastCurrentFragment = homeFragment;
        //初始化
        mRbFunction.setChecked(true);
    }

    private void switchFragment(int checkedId) {
        //可对checkId做判断 区分
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BaseTabFragment fragment = findFragmentById(checkedId);
        if (!fragment.isAdded()) {
            transaction.add(R.id.fl_container, fragment, checkedId + "");
        }
        transaction.show(fragment);
        fragment.onFragmentSelected();
        if (fragment != homeFragment) transaction.hide(homeFragment);
        if (fragment != newsFragment) transaction.hide(newsFragment);
        if (fragment != userFragment) transaction.hide(userFragment);
        transaction.commitAllowingStateLoss();
        lastCurrentFragment = currentFragment;
        currentFragment = fragment;
    }

    private BaseTabFragment findFragmentById(int checkedId) {
        BaseTabFragment fragment = null;
        switch (checkedId) {
            case R.id.rb_home:
                fragment = homeFragment;
                break;
            case R.id.rb_news:
                fragment = newsFragment;
                break;
            case R.id.rb_user:
                fragment = userFragment;
                break;
        }
        return fragment;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackPressed < 3000) {
            super.onBackPressed();
        } else {
            lastBackPressed = System.currentTimeMillis();
            ToastUtils.showShortToast("再按一次退出");
        }
    }

}
