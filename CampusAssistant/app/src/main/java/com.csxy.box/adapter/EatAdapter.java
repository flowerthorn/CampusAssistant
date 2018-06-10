package com.csxy.box.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.base.adapter.BaseRAdapter;
import com.csxy.box.base.adapter.RViewHolder;
import com.csxy.box.bean.BookItem;
import com.csxy.box.bean.EatItem;
import com.csxy.box.bean.EatObj;

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
                gotoMenu(eatItem.getId());
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
                gotoMenu(eatItem.getId());
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
        View root = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_call, null);
        final PopupWindow popupWindow = new PopupWindow(root);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(root);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        TextView tvCall1 =root.findViewById(R.id.tv_call1);
        TextView tvCall2 =root.findViewById(R.id.tv_call2);
        TextView tvCancel=root.findViewById(R.id.tv_cancel);
        final String call1=telephone.substring(0,11);
        tvCall1.setText(call1);
        tvCall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + call1);
                intent.setData(data);
                mContext.startActivity(intent);
            }
        });
        if (telephone.length()>11){
            //两个电话
            final String call2=telephone.substring(12,23);
            tvCall2.setText(call2);
            tvCall2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + call2);
                    intent.setData(data);
                    mContext.startActivity(intent);
                }
            });
        }
        else {

            tvCall2.setVisibility(View.GONE);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    private void gotoMenu(String id) {
    }
}
