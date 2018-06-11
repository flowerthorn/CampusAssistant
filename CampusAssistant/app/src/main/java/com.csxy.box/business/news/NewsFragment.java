package com.csxy.box.business.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.adapter.NewsAdapter;
import com.csxy.box.base.BaseTabFragment;
import com.csxy.box.widget.FunctionView;
import com.csxy.box.widget.RefreshHeaderView;
import com.csxy.box.widget.RefreshFooterView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lihongxin on 2018/5/6.
 */
public class NewsFragment extends BaseTabFragment implements NewsContract.View {

    private NewsContract.Presenter mPresenter;

    @BindView(R.id.functionView)
    FunctionView mFunctionView;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private NewsAdapter newsAdapter;
    private LinearLayoutManager layoutManager;
    protected RefreshFooterView footerView;
    protected RefreshHeaderView headerView;
    private SmartRefreshLayout newsRefreshLayout;
    private RecyclerView newsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, mRootView);
        new NewsPresenter(this);
        initViews(mRootView);
        mPresenter.getNewsList("down");
        return mRootView;
    }

    private void initViews(View mRootView) {
        mFunctionView.showNormalView();
        newsRefreshLayout = mRootView.findViewById(R.id.news_refresh);
        newsRecyclerView = mRootView.findViewById(R.id.news_recyclerView);
        tvTitle.setText("新闻资讯");
        footerView = new RefreshFooterView(getContext());
        headerView = new RefreshHeaderView(getContext());
        newsAdapter = new NewsAdapter(getContext());
        newsRecyclerView.setAdapter(newsAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setItemAnimator(null);
        newsRefreshLayout.setDragRate(0.5f);
        newsRefreshLayout.setHeaderMaxDragRate(5);
        newsRefreshLayout.setRefreshHeader(headerView);
        newsRefreshLayout.setRefreshFooter(footerView);
        newsRefreshLayout.setEnableAutoLoadMore(true);
        newsRefreshLayout.setDisableContentWhenLoading(true);
        newsRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                //下拉刷新
                onPullDownRefresh(refreshLayout);
            }
        });

        newsRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshLayout) {
                //上拉加载
                onPullUpRefresh(refreshLayout);
            }
        });
        mFunctionView.setFailureReloadListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mPresenter.isLoading()) {
                    return;
                }
                mPresenter.getNewsList("down");
            }
        });

    }

    private void onPullUpRefresh(RefreshLayout refreshLayout) {
        //上拉加载
        if (mPresenter.isLoading()) {
            return;
        }
        mPresenter.getNewsList("up");
    }

    private void onPullDownRefresh(RefreshLayout refreshLayout) {
        //下拉刷新
        if (mPresenter.isLoading()) {
            return;
        }
        mPresenter.getNewsList("down");
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public NewsAdapter getAdapter() {
        return newsAdapter;
    }

    @Override
    public void showFailureView() {
        mFunctionView.showFailureView();
    }

    @Override
    public void showNormalView() {
        mFunctionView.showNormalView();
    }

    @Override
    public void showLoadingView() {
        mFunctionView.showLoadingView();
    }

    @Override
    public void finishLoadMore() {
        if (newsRefreshLayout!=null){
            newsRefreshLayout.finishLoadMore(0);
        }
    }

    @Override
    public void finishUpRefesh() {
        if (newsRefreshLayout!=null){
            newsRefreshLayout.finishRefresh(0);
        }
    }

}
