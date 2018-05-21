package com.csxy.box.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.csxy.box.R;


/**
 * Created by wangkang on 18/1/16.
 */

public class FunctionView extends FrameLayout {

    private View loadingView;
    private View failureView;
    private View normalView;
    private FrameLayout coverLayout;


    public FunctionView(@NonNull final Context context) {
        this(context, null);
    }

    public FunctionView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        coverLayout = new FrameLayout(context);
        coverLayout.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FunctionView);
        int resourceId = typedArray.getResourceId(R.styleable.FunctionView_normal_layout, -1);
        int loadingLayoutId = typedArray.getResourceId(R.styleable.FunctionView_loading_layout, R.layout.base_lodingview);
        int failureLayoutId = typedArray.getResourceId(R.styleable.FunctionView_failure_layout, R.layout.base_failureview);
        if (resourceId != -1) {
            View normalView = LayoutInflater.from(getContext()).inflate(resourceId, coverLayout, false);
            setNormalView(normalView);
        }
        typedArray.recycle();

        initView(loadingLayoutId, failureLayoutId);
        setLoading(true);
    }

    private void initView(final int loadingLayoutId, final int failureLayoutId) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        loadingView = inflater.inflate(loadingLayoutId, coverLayout, false);
        failureView = inflater.inflate(failureLayoutId, coverLayout, false);
    }


    public void setNormalView(final View normalView, final ViewGroup.LayoutParams params) {
        this.normalView = normalView;
        normalView.setLayoutParams(params);
    }

    public void setNormalView(final View normalView) {
        this.normalView = normalView;
    }

    public void showNormalView() {
        if (getChildAt(0) !=normalView) {
            addView(normalView,0);
        }
        removeCoverView();

    }

    public void showLoadingView() {
        showCoverView(loadingView);
    }

    public void showFailureView() {
        showCoverView(failureView);
    }



    private void removeCoverView() {
        if (coverLayout.getParent() !=null) {
            removeView(coverLayout);
        }
    }
    private void showCoverView(final View view) {
        if (coverLayout.getParent()==null) {
            addView(coverLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        if (coverLayout.getChildCount() == 1 && coverLayout.getChildAt(0) == view) {
            return;
        }
        coverLayout.removeAllViews();
        coverLayout.addView(view);
    }


    public View getLoadingView() {
        return loadingView;
    }

    public View getFailureView() {
        return failureView;
    }

    public View getNormalView() {
        return normalView;
    }



    public void setLoading(boolean loading) {

    }
    public void setFailureReloadListener(OnClickListener clickListener) {
        getFailureView().findViewById(R.id.repeat_request_btn).setOnClickListener(clickListener);

    }
}
