package com.csxy.box.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.csxy.box.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class RefreshFooterView extends LinearLayout implements RefreshFooter {

    private ProgressBar progressBar;
    private TextView processProgressTextView;

    public RefreshFooterView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public RefreshFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.footer_view_layout, this);

        progressBar = (ProgressBar) findViewById(R.id.foot_progress);
        processProgressTextView = (TextView) findViewById(R.id.foot_process_textView);
    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
//        progressBar.setVisibility(VISIBLE);//开始动画
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        progressBar.setVisibility(GONE);//停止动画
        processProgressTextView.setVisibility(GONE);
        return 200;//延迟500毫秒之后再弹回
    }

    public void setLoadText(String text) {
        processProgressTextView.setText(text);
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullUpToLoad:
                progressBar.setVisibility(VISIBLE);
                processProgressTextView.setVisibility(VISIBLE);
                processProgressTextView.setClickable(false);
                processProgressTextView.setText("上拉加载");
                break;
            case Loading:
                processProgressTextView.setText("正在加载中 . . .");
                break;
            case ReleaseToLoad:
                processProgressTextView.setText("松开加载");
                break;
        }
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
    }

    @Override
    public void onPulling(float v, int i, int i1, int i2) {

    }

    @Override
    public void onReleasing(float v, int i, int i1, int i2) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int i, int i1) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }


    @Override
    public void setPrimaryColors(@ColorInt int... colors) {
    }

    @Override
    public boolean setNoMoreData(boolean b) {
        return false;
    }
}

