package com.csxy.box.business.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.adapter.BookAdapter;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.bean.BookObj;
import com.csxy.box.widget.FunctionView;
import com.csxy.box.widget.RefreshFooterView;
import com.lib.mylibrary.utils.CheckUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class BookResultActivity extends BaseActivity {

    @BindView(R.id.iv_backTb)
    ImageButton ibTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.functionView)
    FunctionView mFunctionView;
    @BindView(R.id.rl_no_book)
    RelativeLayout rlNoBook;

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView mBookRecyclerView;
    private BookAdapter mBookAdapter;
    private LinearLayoutManager layoutManager;
    private int lastItemPosition = 0;
    private TextView tvBookTotal;
    private String bookKey;
    private String type;
    private List<BookObj.BookItem> bookItems;


    public static void actionShow(Context context, String type, List<BookObj.BookItem> bookItemList, String bookKey) {
        Intent intent = new Intent(context, BookResultActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("bookItem", (Serializable) bookItemList);
        intent.putExtra("bookKey", bookKey);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_result);
        ButterKnife.bind(this);
        initDatas();
        initViews();
        lastItemPosition = 0;
        if (type.equals("search_book")) {
            showSearchBookList("new", null, bookItems);
        } else if (type.equals("my_book")) {
            showMyBookList(bookItems);
        }


    }

    private void initDatas() {
        bookKey = getIntent().getStringExtra("bookKey");
        type = getIntent().getStringExtra("type");
        bookItems = (List<BookObj.BookItem>) getIntent().getSerializableExtra("bookItem");
    }

    private void initViews() {
        mFunctionView.showNormalView();

        tvTitle.setText("查询结果");
        ibTitleBack.setVisibility(View.VISIBLE);

        smartRefreshLayout = findViewById(R.id.book_refresh);
        tvBookTotal = findViewById(R.id.tv_book_total);
        mBookRecyclerView = findViewById(R.id.recyclerView_book);
        mBookAdapter = new BookAdapter(this);
        mBookRecyclerView.setAdapter(mBookAdapter);
        layoutManager = new LinearLayoutManager(this);
        mBookRecyclerView.setLayoutManager(layoutManager);

        smartRefreshLayout.setRefreshFooter(new RefreshFooterView(this));
        smartRefreshLayout.setEnableRefresh(false);//禁止下拉刷新
        smartRefreshLayout.setEnableLoadMore(true);//可以上拉加载

        ibTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * @param action        "new"和"old"
     * @param refreshLayout
     * @param bookItemList
     */
    public void showSearchBookList(String action, RefreshLayout refreshLayout, final List<BookObj.BookItem> bookItemList) {
        if (!CheckUtils.isEmpty(bookItemList)) {
            tvBookTotal.setText("为您查找到" + bookItemList.size() + "条【" + bookKey + "】相关的图书结果");
            if (lastItemPosition < bookItemList.size()) {
                if (lastItemPosition + 10 < bookItemList.size()) {
                    mBookAdapter.addData(bookItemList.subList(lastItemPosition, lastItemPosition + 10), action);
                    lastItemPosition += 10;
                } else {
                    mBookAdapter.addData(bookItemList.subList(lastItemPosition, bookItemList.size()), action);
                    lastItemPosition = bookItemList.size();
                }
            } else {
                refreshLayout.finishLoadMore();
                mBookAdapter.showFootView(true);
                smartRefreshLayout.setEnableLoadMore(false);// 全部加载完毕，禁止上拉加载。
            }
        } else {
            rlNoBook.setVisibility(View.VISIBLE);
            tvBookTotal.setText("没有和【" + bookKey + "】相关图书，换个关键词再试一下吧！");
            smartRefreshLayout.setEnableLoadMore(false);// 全部加载完毕，禁止上拉加载。
        }
        //上拉加载
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
                showSearchBookList("old", refreshLayout, bookItemList);
            }
        });
    }

    public void showMyBookList(List<BookObj.BookItem> bookItemList) {

    }


}
