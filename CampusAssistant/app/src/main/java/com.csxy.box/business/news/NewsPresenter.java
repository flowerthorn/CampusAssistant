package com.csxy.box.business.news;

import com.csxy.box.bean.NewsObj;
import com.csxy.box.manager.NetManager;
import com.csxy.box.net.callback.BaseJsonCallback;
import com.csxy.box.utils.ToastUtils;

/**
 * Created by lihongxin on 2018/6/11.
 */
public class NewsPresenter implements NewsContract.Presenter {
    private NewsContract.View mView;
    private boolean isLoading = false;

    public NewsPresenter(NewsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getNewsList(final String action) {
        if (action.equals("down")) {
            //下拉
            mView.showLoadingView();
        }
        isLoading = true;
        NetManager.fetchNews(mView.getContext(), new BaseJsonCallback<NewsObj>(NewsObj.class) {
            @Override
            public void onResponse(String url, NewsObj response, int statusCode) {
                isLoading = false;
                if (response != null) {
                    ToastUtils.showShortToast("为你更新"+response.getResult().getData().size()+"条新内容");
                    mView.getAdapter().addData(response.getResult().getData(), action);
                    mView.showNormalView();
                    if (action.equals("down")){
                        //下拉刷新
                        mView.finishUpRefesh();
                    }
                    else {
                        //上拉
                        mView.finishLoadMore();

                    }
                }
            }

            @Override
            public void onFailure(String url, int statusCode) {
                isLoading = false;
                mView.showFailureView();
            }
        });
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }
}
