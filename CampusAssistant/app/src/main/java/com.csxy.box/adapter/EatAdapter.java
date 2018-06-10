package com.csxy.box.adapter;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.csxy.box.R;
import com.csxy.box.base.adapter.BaseRAdapter;
import com.csxy.box.base.adapter.RViewHolder;
import com.csxy.box.bean.EatItem;
import com.csxy.box.business.eat.MealsActivity;
import com.csxy.box.widget.AlertCenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongxin on 2018/5/31.
 * 食堂档口
 */
public class EatAdapter extends BaseRAdapter {

    private List<EatItem> eatItemList = new ArrayList<>();
    private Context mContext;

    public EatAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return eatItemList.size();
    }

    @Override
    protected int getItemLayout(int position) {
        return R.layout.item_eat;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        final EatItem eatItem = eatItemList.get(position);
        View itemEat = holder.getView(R.id.item_eat);
        TextView tvName = holder.tv(R.id.tv_name);
        TextView tvLocation = holder.tv(R.id.tv_location);
        TextView tvTel = holder.tv(R.id.tv_tel);
        ImageView ivCall = holder.iv(R.id.iv_call);
        ImageView ivMenu = holder.iv(R.id.iv_menu);
        tvName.setText(eatItem.getName());
        tvLocation.setText(eatItem.getFloor());
        tvTel.setText(eatItem.getTelephone());
        if (eatItem.getTelephone().equals("不送餐")) {
            ivCall.setVisibility(View.GONE);
        }

        //点击事件
        itemEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMenu(eatItem.getId(),eatItem.getTelephone());
            }
        });
        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTel(eatItem.getTelephone());
            }
        });
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMenu(eatItem.getId(),eatItem.getTelephone());
            }
        });
    }


    public synchronized void addData(List<EatItem> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        eatItemList = items;
        notifyDataSetChanged();
    }

    private void callTel(String telephone) {
        AlertCenter.showTelPopWindow(mContext,telephone);
    }

    private void gotoMenu(String id,String telephone) {
        MealsActivity.actionShow(mContext,"search_meals",id,telephone);
    }
}
