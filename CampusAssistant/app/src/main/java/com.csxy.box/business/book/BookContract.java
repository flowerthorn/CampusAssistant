package com.csxy.box.business.book;

import com.csxy.box.base.interfaces.BasePresenter;
import com.csxy.box.base.interfaces.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongxin on 2018/5/21.
 */
public interface BookContract {
    interface Presenter extends BasePresenter{
        void initData();
        void searchBook(String str);
        void clearRecentBooks();
        void saveRecentBooks(ArrayList<String> arrayList);
        void searchMyBookList();
    }
    interface View extends BaseView<Presenter>{
        void setSearchInitView(List<String> recentSearchBook,List<String> hotSearchBook);//初始化搜索view
    }
}
