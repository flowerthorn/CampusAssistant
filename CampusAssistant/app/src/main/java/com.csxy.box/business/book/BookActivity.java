package com.csxy.box.business.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.csxy.box.R;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.widget.SearchLayout;
import com.czp.searchmlist.mSearchLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class BookActivity extends BaseActivity implements BookContract.View {


    @BindView(R.id.tv_title)
    TextView tvTitle;

    private SearchLayout mSearchLayout;
    private BookContract.Presenter mPresenter;


    public static void actionShow(Context context) {
        Intent intent = new Intent(context, BookActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        initViews();
        new BookPresenter(this);
        mPresenter.initData();
    }

    private void initViews() {
        mSearchLayout=findViewById(R.id.mSearchlayout);
        tvTitle.setText("图书查询");
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setPresenter(BookContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setSearchInitView(List<String> recentSearchBook, List<String> hotSearchBook) {

        mSearchLayout.initData(recentSearchBook, hotSearchBook, new mSearchLayout.setSearchCallBackListener() {

            @Override
            public void Search(String s) {
                mPresenter.searchBook(s);
            }

            @Override
            public void Back() {
                finish();
            }

            @Override
            public void ClearOldData() {
                //清除历史搜索记录  更新记录原始数据
                mPresenter.clearRecentBooks();
            }

            @Override
            public void SaveOldData(ArrayList<String> arrayList) {
                //保存所有的搜索记录
                mPresenter.saveRecentBooks(arrayList);
            }
        });
    }

    /**
     * 点击查看我收藏的图书
     */
    @OnClick(R.id.rl_mybook)
    public void myBookOnClick() {
        mPresenter.searchMyBookList();
    }


}
