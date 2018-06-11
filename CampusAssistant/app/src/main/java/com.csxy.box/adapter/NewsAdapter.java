package com.csxy.box.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csxy.box.R;
import com.csxy.box.base.adapter.BaseRAdapter;
import com.csxy.box.base.adapter.RViewHolder;
import com.csxy.box.bean.BookItem;
import com.csxy.box.bean.NewsItem;
import com.csxy.box.business.news.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongxin on 2018/6/11.
 */
public class NewsAdapter extends BaseRAdapter {
    private List<NewsItem> newsItemList=new ArrayList<>();
    private Context mContext;
    public NewsAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    @Override
    protected int getItemLayout(int position) {
        return R.layout.item_news;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        final NewsItem newsItem=newsItemList.get(position);
        View view=holder.getView(R.id.ll_item_news);
        ImageView ivImage=holder.iv(R.id.iv_image);
        TextView tvTitle=holder.tv(R.id.tv_title);
        TextView tvAuthorName=holder.tv(R.id.tv_author_name);
        TextView tvDate=holder.tv(R.id.tv_date);
        Glide.with(mContext).load(newsItem.getThumbnail_pic_s()).into(ivImage);
        tvTitle.setText(newsItem.getTitle());
        tvAuthorName.setText(newsItem.getAuthor_name());
        tvDate.setText(newsItem.getDate());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转新闻详情页
                NewsDetailActivity.actionShow(mContext,newsItem);
            }
        });
    }

    public synchronized void addData(List<NewsItem> items, String action) {
        if (items == null || items.size() == 0) {
            return;
        }
        if (action.equals("down")) {
            //下拉
            newsItemList = items;
            notifyDataSetChanged();
        } else {
            //上拉
            int position = newsItemList.size();
            newsItemList.addAll(items);
            notifyItemRangeInserted(position, items.size());
        }
    }
}
