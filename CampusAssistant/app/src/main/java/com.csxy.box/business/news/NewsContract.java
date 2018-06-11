package com.csxy.box.business.news;

import com.csxy.box.adapter.NewsAdapter;
import com.csxy.box.base.interfaces.BasePresenter;
import com.csxy.box.base.interfaces.BaseView;

/**
 * Created by lihongxin on 2018/6/11.
 */
public interface NewsContract {
    interface Presenter extends BasePresenter {
        void getNewsList(String action);
        boolean isLoading();
    }

    interface View extends BaseView<Presenter> {
      NewsAdapter getAdapter();
      void showFailureView();
      void showNormalView();
      void showLoadingView();
      void finishLoadMore();//下拉加载完成
      void finishUpRefesh();//上拉加载完成
    }
}
