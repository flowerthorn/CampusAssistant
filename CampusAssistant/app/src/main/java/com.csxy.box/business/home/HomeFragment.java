package com.csxy.box.business.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.base.BaseTabFragment;
import com.csxy.box.business.course.CourseFragment;
import com.csxy.box.business.function.FunctionFragment;
import com.lib.mylibrary.utils.PhoneUtils;
import com.lib.mylibrary.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lihongxin on 2018/5/6.
 */
public class HomeFragment extends BaseTabFragment {

    @BindView(R.id.vp_home)
    ViewPager mViewPager;
    @BindView(R.id.rg_home)
    RadioGroup mRadioGroup;
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.rb_function)
    RadioButton rbNavigation;
    @BindView(R.id.rb_course)
    RadioButton rbCourse;

    private FunctionFragment functionFragment;// 功能页
    private CourseFragment courseFragment;//课表页
    private FragmentPagerAdapter mPagerAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mRootView);
        initViews();
        return mRootView;
    }

    private void initViews() {
        rbNavigation.setText("功能");
        rbCourse.setText("课表");
        //粗体
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    buttonView.getPaint().setFakeBoldText(true);
                } else {
                    buttonView.getPaint().setFakeBoldText(false);
                }
            }
        };
        rbNavigation.setOnCheckedChangeListener(listener);
        rbCourse.setOnCheckedChangeListener(listener);
        //默认选中radioButton
        rbNavigation.setChecked(true);
        //初始化fragment
        functionFragment = new FunctionFragment();
        courseFragment = new CourseFragment();
        //初始化viewPager的pagerAdapter
        mPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return functionFragment;
                } else {
                    return courseFragment;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        //radioGroup和viewPager相关联
        //radioGroup选中监听
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_function) {
                    mViewPager.setCurrentItem(0, true);
                } else if (i == R.id.rb_course) {
                    mViewPager.setCurrentItem(1, true);
                }
            }
        });
        //viewPager切换监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setScrollLinePosition(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position, false);
                if (position == 0) {
                    mRadioGroup.check(R.id.rb_function);
                } else if (position == 1) {
                    mRadioGroup.check(R.id.rb_course);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //为下划线设置宽度
        int textWidth = getTextWidth(0);
        ivLine.getLayoutParams().width = textWidth;
        //初始化下划线的位置
        setScrollLinePosition(0, 0);
        mViewPager.setCurrentItem(0);
    }

    private void setScrollLinePosition(int position, float offsetPercent) {
        int hostPosition = getRbPosition(position);//第一个rb的位置
        int nextPosition = getRbPosition(position + 1);//第二个rb的位置
        int margin = (int) ((nextPosition - hostPosition) * offsetPercent + hostPosition);
        ((ViewGroup.MarginLayoutParams) ivLine.getLayoutParams()).setMargins(margin, 0, 0, 0);
        ivLine.requestLayout();
    }

    private int getRbPosition(int position) {
        int screenWidth = PhoneUtils.getScreenWidth(getActivity());
        int childWidth = getTextWidth(0);
        float margin = ViewUtils.dip2px(getActivity(),38);
        int childCount = mRadioGroup.getChildCount();
        float edgeSpace = (screenWidth - childWidth * childCount - margin * (childCount - 1)) / 2;
        return (int) (edgeSpace + position * (childWidth + margin));
    }

    public int getTextWidth(int position) {
        if (position > mRadioGroup.getChildCount() - 1) {
            return 0;
        }
        TextView tv = (TextView) mRadioGroup.getChildAt(position);
        return (int) ViewUtils.getTextWidth(tv.getPaint(), tv.getText().toString());
    }
    public void switchToCourseFragment(){
        rbCourse.setChecked(true);
    }


}
