package com.csxy.box.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.bean.Course;
import com.lib.mylibrary.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lihongxin on 2018/6/12.
 */
public class CourseTableView extends RelativeLayout {
    // 课程格子的背景图
    private static final int[] COURSE_BG = {R.drawable.course_info_light_blue, R.drawable.course_info_green,
            R.drawable.course_info_red, R.drawable.course_info_blue, R.drawable.course_info_yellow,
            R.drawable.course_info_orange, R.drawable.course_info_purple};

    private static final int FIRST_TV = 555;

    private static final int FIRST_ROW_TV_QZ = 3;

    private List<? extends Course> coursesData;

    private String[] DAYS = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    /**
     * 和中国星期对应上
     */
    private int[] US_DAYS_NUMS = {7, 1, 2, 3, 4, 5, 6};

    private FrameLayout flCourseContent;

    /**
     * 保存View 方便Remove
     */
    private List<View> myCacheViews = new ArrayList<View>();

    /**
     * 第一行的高度
     **/
    private int firstRowHeight;

    /**
     * 非第一行 每一行的高度
     */
    private int notFirstEveryRowHeight;

    /**
     * 第一列的宽度
     */
    private int firstColumnWidth;

    /**
     * 非第一列 每一列的宽度
     **/
    private int notFirstEveryColumnsWidth;

    private int todayNum;

    private String[] datesOfMonth;

    private int twoW;

    private int oneW;

    private int totalJC = 12;

    private int totalDay = 7;

    private String preMonth;

    public CourseTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CourseTable, defStyleAttr,
                0);
        totalDay = ta.getInt(R.styleable.CourseTable_totalDays, 7);
        totalJC = ta.getInt(R.styleable.CourseTable_totalJC, 12);
        ta.recycle();
        init(context);
        drawFrame();
    }

    public CourseTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseTableView(Context context) {
        this(context, null);
    }

    private OnCourseItemClickListener onCourseItemClickListener;

    public void setOnCourseItemClickListener(OnCourseItemClickListener onCourseItemClickListener) {
        this.onCourseItemClickListener = onCourseItemClickListener;
    }


    public interface OnCourseItemClickListener {
        void onCourseItemClick(TextView tv, int jieci, int day, String des);
    }

    private void init(Context context) {
        Calendar toDayCal = Calendar.getInstance();
        toDayCal.setTimeInMillis(System.currentTimeMillis());
        twoW = ViewUtils.dip2px(context, 2);
        oneW = ViewUtils.dip2px(context, 1);
        todayNum = toDayCal.get(Calendar.DAY_OF_WEEK) - 1;
        datesOfMonth = getOneWeekDatesOfMonth();
    }


    private void drawFrame() {
        initSize();
        // 绘制第一行
        drawFirstRow();
        // 绘制下面的东西,整个下面是一个ScrollView
        addBottomRestView();
    }

    public void updateCourseViews(List<? extends Course> coursesData) {
        this.coursesData = coursesData;
        updateCourseViews();
    }

    private void addBottomRestView() {
        ScrollView sv = new ScrollView(getContext());
        LayoutParams rlp = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, firstTv.getId());
        sv.setLayoutParams(rlp);
        sv.setVerticalScrollBarEnabled(false);

        LinearLayout llBottom = new LinearLayout(getContext());
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        llBottom.setLayoutParams(vlp);

        LinearLayout llLeftCol = new LinearLayout(getContext());
        LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(firstColumnWidth, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        llLeftCol.setLayoutParams(llp1);
        llLeftCol.setOrientation(LinearLayout.VERTICAL);
        initLeftTextViews(llLeftCol);

        llBottom.addView(llLeftCol);

        flCourseContent = new FrameLayout(getContext());
        LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        flCourseContent.setLayoutParams(llp2);
        drawCourseFrame();
        llBottom.addView(flCourseContent);

        sv.addView(llBottom);

        addView(sv);
    }

    private void drawCourseFrame() {
        for (int i = 0; i < totalDay * totalJC; i++) {
            final int row = i / totalDay;
            final int col = i % totalDay;
            FrameLayout fl = new FrameLayout(getContext());
            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(notFirstEveryColumnsWidth,
                    notFirstEveryRowHeight);
            fl.setBackgroundResource(R.drawable.course_table_bg);
            flp.setMargins(col * notFirstEveryColumnsWidth, row * notFirstEveryRowHeight, 0, 0);
            fl.setLayoutParams(flp);
            flCourseContent.addView(fl);
        }
    }

    private void updateCourseViews() {
        clearViewsIfNeeded();
        FrameLayout fl;
        FrameLayout.LayoutParams flp;
        TextView tv;
        for (final Course c : coursesData) {
            final int jieci = c.getJieci();
            final int day = c.getDay();
            fl = new FrameLayout(getContext());
            flp = new FrameLayout.LayoutParams(notFirstEveryColumnsWidth,
                    notFirstEveryRowHeight * c.getSpanNum());
            flp.setMargins((day - 1) * notFirstEveryColumnsWidth, (jieci - 1) * notFirstEveryRowHeight, 0, 0);
            fl.setLayoutParams(flp);
            fl.setPadding(twoW, twoW, twoW, twoW);

            tv = new TextView(getContext());
            flp = new FrameLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
            tv.setText(c.getDes());
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            tv.setPadding(twoW, twoW, twoW, twoW);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setLines(7);
            tv.setBackgroundResource(COURSE_BG[day - 1]);
            tv.setLayoutParams(flp);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCourseItemClickListener != null)
                        onCourseItemClickListener.onCourseItemClick((TextView) v, jieci, day, c.getDes());
                }
            });
            fl.addView(tv);
            myCacheViews.add(fl);
            flCourseContent.addView(fl);
        }
    }

    private void clearViewsIfNeeded() {
        if (myCacheViews == null || myCacheViews.isEmpty())
            return;

        for (int i = myCacheViews.size() - 1; i >= 0; i--) {
            flCourseContent.removeView(myCacheViews.get(i));
            myCacheViews.remove(i);
        }
    }

    private void initLeftTextViews(LinearLayout llLeftCol) {
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(firstColumnWidth, notFirstEveryRowHeight);
        TextView textView;
        for (int i = 0; i < totalJC; i++) {
            textView = new TextView(getContext());
            textView.setLayoutParams(rlp);
            textView.setBackgroundResource(R.drawable.course_table_bg);
            textView.setText("" + (i + 1));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            llLeftCol.addView(textView);
        }
    }

    /**
     * 绘制第一行
     */
    private void drawFirstRow() {
        initFirstTv();
        initRestTv();
    }

    private void initSize() {
        int screenWidth = getScreenWidth();
        int screenHeight = getScreenHeight();
        firstRowHeight = ViewUtils.dip2px(getContext(), 40);
        notFirstEveryColumnsWidth = screenWidth * 2 / (2 * totalDay + 1);
        notFirstEveryRowHeight = (screenHeight - firstRowHeight) / totalJC + ViewUtils.dip2px(getContext(), 5);
        firstColumnWidth = notFirstEveryColumnsWidth / 2;
    }

    /**
     * 获取以今天为基准 ，星期一到星期日在这个月中是几号
     *
     * @return
     */
    private String[] getOneWeekDatesOfMonth() {
        Calendar toDayCal = Calendar.getInstance();
        toDayCal.setTimeInMillis(System.currentTimeMillis());
        String[] temp = new String[totalDay];
        int b = US_DAYS_NUMS[toDayCal.get(Calendar.DAY_OF_WEEK) - 1];
        if (b != 7) { //7是美历的下个星期的周一，而在中国是星期日。如果不为7，则直接拿到这周的周一，如果为7则需要拿到上周的周一(美历)
            toDayCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else {
            toDayCal.add(Calendar.WEEK_OF_MONTH, -1);//跳转到上周周日（美历的周一）
            toDayCal.set(Calendar.DAY_OF_WEEK, 2);//设置为周一（美历的周二）
        }
        int ds = 0;
        for (int i = 1; i < totalDay; i++) {
            if (i == 1) {
                ds = toDayCal.get(Calendar.DAY_OF_MONTH);
                temp[i - 1] = toDayCal.get(Calendar.DAY_OF_MONTH) + "";
                preMonth = (toDayCal.get(Calendar.MONTH) + 1) + "月";
            }
            toDayCal.add(Calendar.DATE, 1);
            if (toDayCal.get(Calendar.DAY_OF_MONTH) < ds) {
                temp[i] = (toDayCal.get(Calendar.MONTH) + 1) + "月";
                ds = toDayCal.get(Calendar.DAY_OF_MONTH);
            } else {
                temp[i] = toDayCal.get(Calendar.DAY_OF_MONTH) + "";
            }
        }
        return temp;
    }

    /**
     * 起始的第一个TextView
     */
    private TextView firstTv;

    @SuppressWarnings("ResourceType")
    private void initFirstTv() {
        firstTv = new TextView(getContext());
        firstTv.setId(FIRST_TV);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(firstColumnWidth, firstRowHeight);
        firstTv.setBackgroundResource(R.drawable.course_table_bg);
        firstTv.setText(preMonth);
        firstTv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        firstTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        firstTv.setPadding(oneW, twoW, oneW, twoW);
        firstTv.setLayoutParams(rlp);
        addView(firstTv);
    }

    private void initRestTv() {
        LinearLayout linearLayout;
        RelativeLayout.LayoutParams rlp;
        LinearLayout.LayoutParams llp;
        TextView tvDate;
        TextView tvDay;
        for (int i = 0; i < totalDay; i++) {
            linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setId(FIRST_ROW_TV_QZ + i);
            rlp = new RelativeLayout.LayoutParams(notFirstEveryColumnsWidth,
                    firstRowHeight);
            if (i == 0)
                rlp.addRule(RelativeLayout.RIGHT_OF, firstTv.getId());
            else
                rlp.addRule(RelativeLayout.RIGHT_OF, FIRST_ROW_TV_QZ + i - 1);
            linearLayout.setBackgroundResource(R.drawable.course_table_bg);
            linearLayout.setLayoutParams(rlp);
            llp = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            tvDate = new TextView(getContext());
            tvDate.setText(datesOfMonth[i]);
            tvDate.setLayoutParams(llp);
            tvDate.setGravity(Gravity.CENTER);
            tvDate.setPadding(twoW, twoW, twoW, twoW);
            tvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            linearLayout.addView(tvDate);
            llp = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
            tvDay = new TextView(getContext());
            tvDay.setLayoutParams(llp);
            tvDay.setText(DAYS[i]);
            tvDay.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            if (US_DAYS_NUMS[todayNum] - 1 == i) {
                linearLayout.setBackgroundColor(0x77069ee9);
                tvDay.setTextColor(Color.WHITE);
                tvDate.setTextColor(Color.WHITE);
            }
            tvDay.setPadding(twoW, 0, twoW, twoW * 2);
            tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            linearLayout.addView(tvDay);
            addView(linearLayout);
        }
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public void setTotalJC(int totalJC) {
        this.totalJC = totalJC;
        refreshCurrentLayout();
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
        refreshCurrentLayout();
    }

    private void refreshCurrentLayout() {
        removeAllViews();
        init(getContext());
        drawFrame();
        updateCourseViews();
    }

}
