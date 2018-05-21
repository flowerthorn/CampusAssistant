package com.csxy.box.business.book;

import com.csxy.box.bean.BookObj;
import com.csxy.box.manager.NetManager;
import com.csxy.box.net.callback.BaseJsonCallback;
import com.csxy.box.utils.ToastUtils;
import com.lib.mylibrary.utils.SpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class BookPresenter implements BookContract.Presenter {
    private BookContract.View mBookView;
    private List<String> recentSearchBook;
    private List<String> hotSearchBook;


    public BookPresenter(BookContract.View mBookView) {
        this.mBookView = mBookView;
        mBookView.setPresenter(this);
    }

    @Override
    public void initData() {
        //初始化热门图书搜索
        String hotData = "Android,数据结构,黑客技术,python,计算机,经济管理,小说";
        hotSearchBook= Arrays.asList(hotData.split(","));
        //数据库获取最近搜索
        recentSearchBook= SpUtils.getList("recentSearchBook");
        mBookView.setSearchInitView(recentSearchBook, hotSearchBook);
    }

    @Override
    public void searchBook(final String str) {
        NetManager.searchBook(mBookView.getContext(), new BaseJsonCallback<BookObj>(BookObj.class) {
            @Override
            public void onResponse(String url, BookObj response, int statusCode) {
                if (response!=null){
                    BookResultActivity.actionShow(mBookView.getContext(),"search_book",response.getBook(),str);
                }
            }

            @Override
            public void onFailure(String url, int statusCode) {
                ToastUtils.showLongToast("请检查您的网络");
            }
        }, str);
    }

    @Override
    public void clearRecentBooks() {
        SpUtils.remove("recentSearchBook");
    }

    @Override
    public void saveRecentBooks(ArrayList<String> arrayList) {
        SpUtils.putList("recentSearchBook",arrayList);
    }

    @Override
    public void searchMyBookList() {

    }

    @Override
    public List<String> getRecentSearchBook() {
        return recentSearchBook;
    }

    @Override
    public List<String> getHotSearchBook() {
        return hotSearchBook;
    }
}
