package com.csxy.box.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.czp.searchmlist.FlowLayout;
import com.czp.searchmlist.SearchOldDataAdapter;
import com.czp.searchmlist.selfSearchGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lihongxin on 2018/5/21.
 */


public class SearchLayout extends LinearLayout {
    private String msearch_hint;
    private int msearch_baground;
    Context context;
    private ImageView ib_searchtext_delete;
    private EditText et_searchtext_search;
    private LinearLayout searchview;
    private Button bt_text_search_back;
    private TextView tvclearolddata;
    private selfSearchGridView gridviewolddata;
    private SearchOldDataAdapter OldDataAdapter;
    private ArrayList<String> OldDataList = new ArrayList();
    FlowLayout hotflowLayout;
    private String backtitle = "取消";
    private String searchtitle = "搜索";
    private OnClickListener TextViewItemListener;
    private int countOldDataSize = 15;
    private com.czp.searchmlist.mSearchLayout.setSearchCallBackListener sCBlistener;

    public SearchLayout(Context context) {
        super(context);
        this.context = context;
        this.InitView();
    }

    public SearchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, com.czp.searchmlist.R.styleable.searchmlist);
        this.msearch_hint = ta.getString(com.czp.searchmlist.R.styleable.searchmlist_search_hint);
        this.msearch_baground = ta.getResourceId(com.czp.searchmlist.R.styleable.searchmlist_search_baground, com.czp.searchmlist.R.drawable.search_baground_shap);
        ta.recycle();
        this.InitView();
    }

    public SearchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.InitView();
    }

    private void InitView() {
        this.backtitle = this.getResources().getString(com.czp.searchmlist.R.string.search_cancel);
        this.searchtitle = this.getResources().getString(com.czp.searchmlist.R.string.search_verify);
        this.searchview = (LinearLayout) LayoutInflater.from(this.context).inflate(com.czp.searchmlist.R.layout.msearchlayout, (ViewGroup) null);
        this.addView(this.searchview);
        this.ib_searchtext_delete = (ImageView) this.searchview.findViewById(com.czp.searchmlist.R.id.ib_searchtext_delete);
        this.et_searchtext_search = (EditText) this.searchview.findViewById(com.czp.searchmlist.R.id.et_searchtext_search);
        this.et_searchtext_search.setBackgroundResource(this.msearch_baground);
        this.et_searchtext_search.setHint(this.msearch_hint);
        this.bt_text_search_back = (Button) this.searchview.findViewById(com.czp.searchmlist.R.id.buttonback);
        this.tvclearolddata = (TextView) this.searchview.findViewById(com.czp.searchmlist.R.id.tvclearolddata);
        this.gridviewolddata = (selfSearchGridView) this.searchview.findViewById(com.czp.searchmlist.R.id.gridviewolddata);
        this.gridviewolddata.setSelector(new ColorDrawable(0));
        this.hotflowLayout = (FlowLayout) this.searchview.findViewById(com.czp.searchmlist.R.id.id_flowlayouthot);
        this.setLinstener();
    }

    private void setLinstener() {
        this.ib_searchtext_delete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                et_searchtext_search.setText("");
            }
        });
        this.et_searchtext_search.addTextChangedListener(new MyTextWatcher());
        this.et_searchtext_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 3 && (event == null || event.getKeyCode() != 66)) {
                    return false;
                } else {
                    String searchtext = et_searchtext_search.getText().toString().trim();
                    executeSearch_and_NotifyDataSetChanged(searchtext);
                    return true;
                }
            }
        });
        this.bt_text_search_back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String searchtext = et_searchtext_search.getText().toString().trim();
                if (bt_text_search_back.getText().toString().equals(searchtitle)) {
                    executeSearch_and_NotifyDataSetChanged(searchtext);
                } else if (sCBlistener != null) {
                    sCBlistener.Back();
                }

            }
        });
        this.tvclearolddata.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (sCBlistener != null) {
                    OldDataList.clear();
                    OldDataAdapter.notifyDataSetChanged();
                    sCBlistener.ClearOldData();
                }

            }
        });
        this.TextViewItemListener = new OnClickListener() {
            public void onClick(View v) {
                String string = ((TextView) v).getText().toString();
                executeSearch_and_NotifyDataSetChanged(string);
            }
        };
        this.gridviewolddata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sCBlistener != null) {
                    sCBlistener.Search(((String) OldDataList.get(position)).trim());
                }

            }
        });
    }

    public void initData(List<String> olddatalist, List<String> hotdata, com.czp.searchmlist.mSearchLayout.setSearchCallBackListener sCb) {
        this.SetCallBackListener(sCb);
        this.hotflowLayout.removeAllViews();
        this.OldDataList.clear();
        if (olddatalist != null) {
            this.OldDataList.addAll(olddatalist);
        }

        this.OldDataAdapter = new SearchOldDataAdapter(this.context, this.OldDataList);
        this.gridviewolddata.setAdapter(this.OldDataAdapter);
        LayoutInflater mInflater = LayoutInflater.from(this.context);

        for (int i = 0; i < hotdata.size(); ++i) {
            TextView tv = (TextView) mInflater.inflate(com.czp.searchmlist.R.layout.suosou_item, this.hotflowLayout, false);
            tv.setText((CharSequence) hotdata.get(i));
            tv.setOnClickListener(this.TextViewItemListener);
            tv.getBackground().setLevel(this.MyRandom(1, 5));
            this.hotflowLayout.addView(tv);
        }

    }

    private void executeSearch_and_NotifyDataSetChanged(String str) {
        if (this.sCBlistener != null && !str.equals("")) {
            if (this.OldDataList.size() <= 0 || !((String) this.OldDataList.get(0)).equals(str)) {
                if (this.OldDataList.size() == this.countOldDataSize && this.OldDataList.size() > 0) {
                    this.OldDataList.remove(this.OldDataList.size() - 1);
                }
                if (!OldDataList.contains(str)) {
                    this.OldDataList.add(0, str);
                    this.OldDataAdapter.notifyDataSetChanged();
                    this.sCBlistener.SaveOldData(this.OldDataList);
                }
            }

            this.sCBlistener.Search(str);
        }

    }

    public int MyRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    private void SetCallBackListener(com.czp.searchmlist.mSearchLayout.setSearchCallBackListener sCb) {
        this.sCBlistener = sCb;
    }

    public interface setSearchCallBackListener {
        void Search(String var1);

        void Back();

        void ClearOldData();

        void SaveOldData(ArrayList<String> var1);
    }

    private class MyTextWatcher implements TextWatcher {
        private MyTextWatcher() {
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                ib_searchtext_delete.setVisibility(VISIBLE);
                bt_text_search_back.setText(searchtitle);
            } else {
                ib_searchtext_delete.setVisibility(GONE);
                bt_text_search_back.setText(backtitle);
            }

        }
    }
}
