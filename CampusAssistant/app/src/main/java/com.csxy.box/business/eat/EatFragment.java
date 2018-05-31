package com.csxy.box.business.eat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lihongxin on 2018/5/31.
 */
public class EatFragment extends BaseFragment {
    @BindView(R.id.text)
    TextView mTextView;
    private int mType;
    public static EatFragment newInstance(int testType) {
        EatFragment fragment = new EatFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", testType);
        fragment.setArguments(bundle);
        return fragment;
    }


    public void onAttach(Activity context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eat, container, false);
        ButterKnife.bind(this,view);
        mTextView.setText("这是第" + mType + "个Fragment");
        return view;
    }
}
