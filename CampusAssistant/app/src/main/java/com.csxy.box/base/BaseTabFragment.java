package com.csxy.box.base;

import android.os.Handler;
import android.os.Message;
import com.csxy.box.app.MyApplication;
import com.csxy.box.utils.L;

import java.util.Date;

/**
 * MainActivity中加载的Fragment继承的基类，主要实现被选择时的数据刷新，避免快速重复刷新
 */

public class BaseTabFragment extends BaseFragment {

    private static final String TAG = MyApplication.TAG + BaseTabFragment.class.getSimpleName();

    private static final int MSG_RESUME = 0;

    private long mSelectedTime = 0;

    public void onFragmentSelected() {
        if (!isResumed()) return;
        long lastTime = mSelectedTime;
        mSelectedTime = new Date().getTime();
        if (mSelectedTime - lastTime < 3000) {
            mHandler.removeMessages(MSG_RESUME);
            mHandler.sendEmptyMessageDelayed(MSG_RESUME, 3000);
        } else {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d(TAG, "BaseTabFragment onResume");
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (!isResumed()) return;

            if (msg.what == MSG_RESUME) onResume();
        }
    };

}
