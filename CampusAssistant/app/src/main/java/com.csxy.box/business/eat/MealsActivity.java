package com.csxy.box.business.eat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.adapter.MealsAdapter;
import com.csxy.box.base.BaseActivity;
import com.csxy.box.bean.MealItem;
import com.csxy.box.bean.MealObj;
import com.csxy.box.manager.NetManager;
import com.csxy.box.net.callback.BaseJsonCallback;
import com.csxy.box.utils.ToastUtils;
import com.lib.mylibrary.utils.CheckUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lihongxin on 2018/6/10.
 */
public class MealsActivity extends BaseActivity {
    @BindView(R.id.iv_backTb)
    ImageButton ibTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView_meal)
    RecyclerView recyclerViewEat;
    private MealsAdapter mealsAdapter;
    private LinearLayoutManager layoutManager;

    private String type;
    private String id;
    private String telephone;

    public static void actionShow(Context context, String type, String id, String telephone) {
        Intent intent = new Intent(context, MealsActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        intent.putExtra("telephone", telephone);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        telephone = getIntent().getStringExtra("telephone");
        initViews();
        if (type.equals("search_meals")) {
            showSearchMealsList();
        } else if (type.equals("my_meals")) {
            showMyMealList();
        }

    }

    private void showMyMealList() {
        List<MealItem> allCollectMeals = DataSupport.order("id desc").find(MealItem.class);
        if ( CheckUtils.isEmpty(allCollectMeals)){
            recyclerViewEat.setVisibility(View.GONE);
        }
        else {
            mealsAdapter.addData(allCollectMeals,  allCollectMeals.get(0).getTel());
        }
    }

    private void showSearchMealsList() {
        NetManager.searchMeals(mContext, new BaseJsonCallback<MealObj>(MealObj.class) {
            @Override
            public void onResponse(String url, MealObj response, int statusCode) {
                if (response != null) {
                    mealsAdapter.addData(response.getData(), telephone);
                }
            }

            @Override
            public void onFailure(String url, int statusCode) {
                ToastUtils.showLongToast("网络错误，请检查网络上设置");
            }
        }, id);

    }

    private void initViews() {
        if (type.equals("search_meals")) {
            //查看餐品
            tvTitle.setText("餐品查看");
        } else if (type.equals("my_meals")) {
            //查看我最爱吃
            tvTitle.setText("我最爱吃");
        }
        mealsAdapter = new MealsAdapter(mContext);
        recyclerViewEat.setAdapter(mealsAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewEat.setLayoutManager(layoutManager);
        ibTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
