package com.csxy.box.business.user;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.base.BaseTabFragment;
import com.csxy.box.pref.UserPreferences;
import com.csxy.box.utils.ToastUtils;
import com.csxy.box.widget.alert.AlertCenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihongxin on 2018/5/6.
 */
public class UserFragment extends BaseTabFragment implements UserContract.View {
    private UserContract.Presenter mPresenter;
    private Dialog exitDialog;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.rl_exit)
    RelativeLayout rlExit;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, mRootView);
        new UserPresenter(this);
        mPresenter.registerLoginReceiver();
        mPresenter.checkLogin();
        return mRootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.ll_scenery)
    public void sceneryOnClick() {
        mPresenter.gotoScener();
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.ll_update)
    public void updateOnClick() {
        ToastUtils.showLongToast("已是最新版本");
        // TODO: 2018/6/11 提供检查更新接口
    }
    @OnClick(R.id.ll_feedback)
    public void feedbackOnClick(){
        mPresenter.gotoFeedBack();
    }
    @OnClick(R.id.ll_guess)
    public void guessOnClick(){
        mPresenter.gotoGuess();
    }
    @OnClick(R.id.ll_about)
    public void aboutOnClick(){
        mPresenter.gotoAboutUs();
    }

    @Override
    public void showLoginView() {
        tvName.setText(UserPreferences.getUserName());
        tvId.setText("学号:"+UserPreferences.getId());
        tvLogin.setVisibility(View.GONE);
        rlExit.setVisibility(View.VISIBLE);
        ivAvatar.setImageResource(R.drawable.me_avatar);
        rlExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exitDialog == null) {
                    exitDialog = AlertCenter.getExitWxDialog(getContext(), new AlertCenter.Callback<Dialog>() {
                        @Override
                        public void onCallback(Dialog obj) {
                            obj.dismiss();
                            mPresenter.gotoExit();

                        }
                    });
                    exitDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            exitDialog = null;
                        }
                    });
                }
                if (!exitDialog.isShowing()) {
                    exitDialog.show();
                }

            }
        });
    }

    @Override
    public void showNoLoginView() {
        tvName.setText("游客");
        tvId.setText("学号:无");
        tvLogin.setVisibility(View.VISIBLE);
        rlExit.setVisibility(View.GONE);
        ivAvatar.setImageResource(R.drawable.default_avatar);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.gotoLogin();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
