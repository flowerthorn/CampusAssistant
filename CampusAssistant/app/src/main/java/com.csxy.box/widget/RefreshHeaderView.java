package com.csxy.box.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.app.MyApplication;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import java.lang.ref.WeakReference;

/**
 * Created by lihongxin on 2018/6/11.
 */
public class RefreshHeaderView extends LinearLayout implements RefreshHeader {

    private ImageView processProgressImageView;
    private TextView processProgressTextView;
    private AnimationDrawable animationDrawable;

    private RefreshKernel refreshKernel;
    private static Handler textHandler = new TextHandler();


    public RefreshHeaderView(Context context) {
        super(context);
        initView();
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }

    public RefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header_view_layout, this);
        processProgressImageView =  findViewById(R.id.process_progress_img);
        processProgressTextView =  findViewById(R.id.process_progress_textView);
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
        //开始动画
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        //停止动画
        return 0;//延迟500毫秒之后再弹回
    }

    public void setRefreshText(String text) {
//        finishProgressTextView.setText(text);
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        textHandler.removeCallbacksAndMessages(null);
        switch (newState) {
            case None:
            case PullDownToRefresh:
                processProgressImageView.setImageResource(R.drawable.ptr_0);
                processProgressTextView.setText("下拉刷新");
                break;
            case Refreshing:
                processProgressImageView.setImageResource(R.drawable.refresh_loading);
                animationDrawable = (AnimationDrawable) processProgressImageView.getDrawable();
                animationDrawable.start();
                processProgressTextView.setText("加载中");
                Message obtain = Message.obtain();
                obtain.arg1 = 0;
                obtain.arg2 = 1;
                obtain.obj = new WeakReference<>(processProgressTextView);
                textHandler.sendMessageDelayed(obtain, TextHandler.INTERVAL);
                break;
            case ReleaseToRefresh:
                processProgressTextView.setText("松开刷新");
                break;
        }
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        if (refreshKernel == null) {
            refreshKernel = kernel;
        }
    }

    @Override
    public void onPulling(float v, int i, int i1, int i2) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }


    @Override
    public void onReleasing(float percent, int offset, int headHeight, int extendHeight) {
    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int i, int i1) {

    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {
    }

    private static class TextHandler extends Handler {
        private static final int MAX_POINT = 4;
        static final int INTERVAL = 200;

        @Override
        public void handleMessage(final Message msg) {
            WeakReference<TextView> obj = (WeakReference<TextView>) msg.obj;
            TextView textView = obj.get();
            if (textView == null) {
                return;
            }
            String text = "";
            if (msg.arg1 == 0) {
                text ="加载中";
            }
            for (int i = 0; i < msg.arg2; i++) {
                text += ".";
            }
            textView.setText(text);
            Message newMsg = Message.obtain(msg);
            newMsg.arg2 = msg.arg2 + 1 > MAX_POINT ? 0 : msg.arg2 + 1;
            newMsg.getTarget().sendMessageDelayed(newMsg, INTERVAL);
        }
    }
}

