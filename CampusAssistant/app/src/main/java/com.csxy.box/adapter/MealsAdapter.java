package com.csxy.box.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.base.adapter.BaseRAdapter;
import com.csxy.box.base.adapter.RViewHolder;
import com.csxy.box.bean.MealItem;
import com.csxy.box.utils.L;
import com.csxy.box.widget.alert.AlertCenter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongxin on 2018/5/31.
 * 档口餐品
 */
public class MealsAdapter extends BaseRAdapter {
    private List<MealItem> mealItemList = new ArrayList<>();
    private String telephone;
    private String fromType;
    private Context mContext;

    public MealsAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mealItemList.size();
    }

    @Override
    protected int getItemLayout(int position) {
        return R.layout.item_meal;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, final int position) {
        final MealItem mealItem = mealItemList.get(position);
        TextView tvName = holder.tv(R.id.tv_name);
        TextView tvPrice = holder.tv(R.id.tv_price);
        ImageView ivCall = holder.iv(R.id.iv_call);
        final ImageView ivCollect = holder.iv(R.id.iv_collect);
        tvName.setText(mealItem.getName());
        tvPrice.setText(mealItem.getPrice());
        if (fromType.equals("my_meal")){
            //来自我的收藏
            telephone=mealItem.getTel();
        }
        if (telephone.equals("不送餐") || TextUtils.isEmpty(telephone)) {
            ivCall.setVisibility(View.GONE);
        } else {
            ivCall.setVisibility(View.VISIBLE);
        }

        ivCall.setTag(mealItem.getName());
        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertCenter.showTelPopWindow(mContext, telephone);
            }
        });
        //是否收藏
        if (DataSupport.isExist(MealItem.class, "name = ?", mealItem.getName())) {
            //已经收藏
            ivCollect.setImageResource(R.drawable.meal_collect_finish);
        } else {
            //未收藏
            ivCollect.setImageResource(R.drawable.meal_collect);
        }

        ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消收藏
                if (DataSupport.isExist(MealItem.class, "name = ?", mealItem.getName())) {
                    //ui取消
                    ivCollect.setImageResource(R.drawable.meal_collect);
                    //数据库操作
                    DataSupport.deleteAll(MealItem.class, "name= ?", mealItem.getName());

                }
                //收藏
                else {
                    ivCollect.setImageResource(R.drawable.meal_collect_finish);
                    MealItem meal = new MealItem();
                    meal.setName(mealItem.getName());
                    meal.setTel(telephone);
                    meal.setPrice(mealItem.getPrice());
                    meal.save();
                }
            }
        });
    }

    public synchronized void addData(List<MealItem> items, String tel, String type) {
        if (items == null || items.size() == 0) {
            return;
        }
        mealItemList = items;
        telephone = tel;
        fromType = type;
        notifyDataSetChanged();
    }
}
