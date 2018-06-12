package com.csxy.box.business.course;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csxy.box.R;
import com.csxy.box.base.BaseTabFragment;
import com.csxy.box.bean.Course;
import com.csxy.box.widget.CourseTableView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lihongxin on 2018/5/6.
 */
public class CourseFragment extends BaseTabFragment implements CourseContract.View {

    private CourseContract.Presenter mPresenter;
    @BindView(R.id.tableView)
    CourseTableView courseTableView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_course, container, false);
        ButterKnife.bind(this, mRootView);
        new CoursePresenter(this);
        initData();

        return mRootView;
    }

    private void initData() {
        //假数据 校网接口暂不能用
        List<Course> list = new ArrayList<>();
        Course c1 = new Course();
        c1.setDay(1);
        c1.setDes("Hibernate+spring\n" + "吕海东\n" +
                "教学楼1514");
        c1.setJieci(1);
        list.add(c1);

        Course c2 = new Course();
        c2.setDay(1);
        c2.setDes("计算机网络\n" +
                "敖磊\n" +
                "教学楼1406");
        c2.setJieci(5);
        list.add(c2);

        Course c3 = new Course();
        c3.setDay(2);
        c3.setDes("ios应用开发（二）\n" +
                "姜立秋\n" +
                "实验楼1001");
        c3.setJieci(3);
        list.add(c3);

        Course c4 = new Course();
        c4.setDay(2);
        c4.setDes("Hibernate+spring\n" +
                "吕海东\n" +
                "实验楼302机房");
        c4.setJieci(1);
        list.add(c4);

        Course c5 = new Course();
        c5.setDay(3);
        c5.setDes("计算机网络\n" +
                "敖磊\n" +
                "教学楼1406");
        c5.setJieci(1);
        list.add(c5);

        Course c6 = new Course();
        c6.setDay(3);
        c6.setDes("Linux系统管理\n" +
                "张坤\n" +
                "实验楼1112");
        c6.setJieci(3);
        list.add(c6);

        Course c7 = new Course();
        c7.setDay(4);
        c7.setDes("Struts架构技术\n" +
                "唐琳\n" +
                "教学楼1406");
        c7.setJieci(1);
        list.add(c7);

        Course c8 = new Course();
        c8.setDay(4);
        c8.setDes("软件测试\n" +
                "张应博\n" +
                "教学楼1406");
        c8.setJieci(3);
        list.add(c8);

        Course c9 = new Course();
        c9.setDay(4);
        c9.setDes("ios应用开发（一）\n" +
                "赵慧然\n" +
                "实验楼1001");
        c9.setJieci(7);

        Course c10 = new Course();
        c10.setDay(5);
        c10.setDes("ios应用开发（二）\n" +
                "姜立秋\n" +
                "实验楼1001");
        c10.setJieci(7);
        list.add(c10);

        Course c11 = new Course();
        c11.setDay(6);
        c11.setDes("心理讲座 \n"+"图书馆报告厅\n");
        c11.setJieci(9);
        c11.setSpanNum(4);
        list.add(c11);


        courseTableView.updateCourseViews(list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(CourseContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
