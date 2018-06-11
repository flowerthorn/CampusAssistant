package com.csxy.box.business.course;

/**
 * Created by lihongxin on 2018/6/11.
 */
public class CoursePresenter implements CourseContract.Presenter {
    private CourseContract.View mView;

    public CoursePresenter(CourseContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }
}
