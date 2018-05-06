package com.csxy.box.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


/**
 * Created by admin on 2016/12/9.
 */
public class RViewHolder extends RecyclerView.ViewHolder  {

    private final Context mContext;
    private final int mLayoutId;
    private SparseArray<View> mViews;

    public RViewHolder(View itemView, Context context, int layoutId) {
        super(itemView);
        mViews = new SparseArray();
        this.mContext = context;
        this.mLayoutId = layoutId;
    }

    public <T extends View> T getView(int id) {
        T t = (T) mViews.get(id);
        if (t == null) {
            t = (T) itemView.findViewById(id);
            mViews.put(id, t);
        }
        return t;
    }

    public ImageView iv(int id) {
        ImageView iv = getView(id);
        return iv;
    }

    public TextView tv(int id) {
        TextView tv = getView(id);
        return tv;
    }

    public CheckBox cb(int id) {
        CheckBox cb = getView(id);
        return cb;
    }

    public RadioButton rb(int id) {
        RadioButton rb = getView(id);
        return rb;
    }

    public Button bt(int id) {
        Button bt = getView(id);
        return bt;
    }

    public int getLayoutId() {
        return mLayoutId;
    }
}
