package com.csxy.box.business.eat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.csxy.box.R;
import com.csxy.box.adapter.EatFragmentPagerAdapter;
import com.csxy.box.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.support.v4.app.Fragment;
/**
 * Created by lihongxin on 2018/5/31.
 */
public class EatActivity extends BaseActivity implements EatContract.View {

    private EatContract.Presenter mPresenter;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.eat_viewPager)
    ViewPager mViewPager;
    private List<String> titlelist=new ArrayList<>();
    private List<Fragment> fragmentList=new ArrayList<>();

    public static void actionShow(Context context){
        Intent intent=new Intent(context,EatActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);
        ButterKnife.bind(this);
        setStatusbarColor(R.color.yellow_status_bar, true);
        initView();
        new EatPresenter(this);
    }

    private void initView() {
        titlelist.add("二食堂");
        titlelist.add("三食堂");
        fragmentList.add(EatFragment.newInstance(1));
        fragmentList.add(EatFragment.newInstance(2));
        //为viewPager设置适配器
        EatFragmentPagerAdapter eatFragmentPagerAdapter=new EatFragmentPagerAdapter(getSupportFragmentManager(),titlelist,fragmentList);
        mViewPager.setAdapter(eatFragmentPagerAdapter);
        //为TabLayout设置ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        //使用ViewPager的适配器
        mTabLayout.setTabsFromPagerAdapter(eatFragmentPagerAdapter);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setPresenter(EatContract.Presenter presenter) {
        mPresenter = presenter;
    }
    @OnClick(R.id.fab)
    public void fabOnClick(){
        mPresenter.gotoMyEatCollect();
    }
}
