package com.csxy.box.business.eat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.csxy.box.R;
import com.csxy.box.adapter.EatAdapter;
import com.csxy.box.base.BaseFragment;
import com.csxy.box.bean.EatObj;
import com.csxy.box.manager.NetManager;
import com.csxy.box.net.callback.BaseJsonCallback;
import com.csxy.box.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lihongxin on 2018/5/31.
 */
public class EatFragment extends BaseFragment {
    @BindView(R.id.recyclerView_eat)
    RecyclerView mRecyclerView;
    private EatAdapter mEatAdapter;
    private LinearLayoutManager layoutManager;

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
        ButterKnife.bind(this, view);
        mEatAdapter = new EatAdapter(getContext());
        mRecyclerView.setAdapter(mEatAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        getList(mType);
        return view;
    }

    private void getList(final int mType) {
        NetManager.searchEat(getContext(), new BaseJsonCallback<EatObj>(EatObj.class) {
            @Override
            public void onResponse(String url, EatObj response, int statusCode) {
                if (response != null) {
                    if (mType == 1) {
                        mEatAdapter.addData(response.getYy().get(0).getData());
                    } else {
                        mEatAdapter.addData(response.getYy().get(1).getData());
                    }
                } else {
                    ToastUtils.showLongToast("网络加载错误，请检查网络连接");
                }
            }

            @Override
            public void onFailure(String url, int statusCode) {
                ToastUtils.showLongToast("网络加载错误，请检查网络连接");
            }
        });
    }
}
