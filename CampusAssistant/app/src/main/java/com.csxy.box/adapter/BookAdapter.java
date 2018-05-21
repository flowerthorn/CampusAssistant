package com.csxy.box.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.csxy.box.R;
import com.csxy.box.base.adapter.BaseRAdapter;
import com.csxy.box.base.adapter.RViewHolder;
import com.csxy.box.bean.BookObj;
import com.csxy.box.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class BookAdapter extends BaseRAdapter {
    private List<BookObj.BookItem> bookItems = new ArrayList<>();
    private boolean isShowFoot = false;
    public BookAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        if (isShowFoot) {
            return bookItems.size() + 1;
        } else {
            return bookItems.size();
        }
    }

    @Override
    protected int getItemLayout(int position) {
        if (isShowFoot&& position == bookItems.size()){
            return R.layout.item_footview;
        }
        return R.layout.item_book;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, final int position) {
        if (!(isShowFoot&& position == bookItems.size())){
            View view=holder.getView(R.id.item_book);
            TextView tvName=holder.tv(R.id.item_book_name);
            TextView tvPress=holder.tv(R.id.item_book_press);
            TextView tvLocation=holder.tv(R.id.item_book_location);
            TextView tvDate=holder.tv(R.id.item_book_date);
            TextView tvAuthor = holder.tv(R.id.item_book_author);
            TextView tvState=holder.tv(R.id.item_book_state);
            TextView tvSearch=holder.tv(R.id.item_book_search);
            tvName.setText(bookItems.get(position).getTitle());
            tvAuthor.setText(bookItems.get(position).getAuther());
            tvDate.setText(bookItems.get(position).getTime());
            tvLocation.setText(bookItems.get(position).getPlace());
            tvSearch.setText(bookItems.get(position).getSearch());
            tvPress.setText(bookItems.get(position).getPress());
            if (bookItems.get(position).getState().equals("可借")){
                tvState.setText("借阅状态:可借");
                tvState.setTextColor(mContext.getResources().getColor(R.color.state_green));
            }
            else {
                tvState.setText("借阅状态:不可借");
                tvState.setTextColor(mContext.getResources().getColor(R.color.state_red));
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showLongToast("点击了"+bookItems.get(position).getTitle());
                }
            });

        }
    }

    public synchronized void addData(List<BookObj.BookItem> items,String action) {
        if (items == null || items.size() == 0) {
            return;
        }
        isShowFoot = false;
        if (action.equals("clear")){
            bookItems = items;
            notifyDataSetChanged();
        }
        else {
            int position=bookItems.size();
            bookItems.addAll(items);
            notifyItemRangeInserted(position, items.size());
        }

    }
    public synchronized void showFootView(boolean show) {
        isShowFoot = show;
        if (isShowFoot) {
            notifyItemInserted(bookItems.size());
        }
    }

}
