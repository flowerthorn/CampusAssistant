package com.csxy.box.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


/**
 * Created by wangkang on 17/3/29.
 */

public abstract class BaseRAdapter extends RecyclerView.Adapter<RViewHolder> {

    protected final Context mContext;

    protected AdapterView.OnItemClickListener mOnItemClickListener;

    public BaseRAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflateView(parent, viewType);
        RViewHolder holder = new RViewHolder(view, mContext, viewType);
        return holder;
    }

    protected View inflateView(ViewGroup parent, int layoutId) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return layoutInflater.inflate(layoutId, parent, false);
    }

    @Override
    public int getItemViewType(int position) {
        return getItemLayout(position);
    }

    @Override
    public abstract int getItemCount();

    protected abstract int getItemLayout(int position);

    @Override
    public abstract void onBindViewHolder(RViewHolder holder, int position);

}
