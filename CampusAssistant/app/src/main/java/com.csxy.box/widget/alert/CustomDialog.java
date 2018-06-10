package com.csxy.box.widget.alert;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.csxy.box.R;
import com.csxy.box.utils.AppUtils;
import com.lib.mylibrary.utils.ViewUtils;


public class CustomDialog extends Dialog {


    public CustomDialog(Context context, int theme, View view) {
        super(context, theme);
        addContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

    }
    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }


    @Override
    public void show() {
        Context context = getContext();
        if (AppUtils.activityIsFinishing(context)) {
            return;
        }
        super.show();

    }


    @Override
    public void dismiss() {
        Context context = getContext();
        if (AppUtils.activityIsFinishing(context)) {
            return;
        }
        super.dismiss();
    }

    public static class Builder {


        private Object title;
        private Object content;
        private Object positiveButtonText;
        private Object negativeButtonText;

        private boolean showPositiveButton = true;
        private boolean showNegativeButton = true;
        private boolean showCancelIcon = true;
        private boolean positiveOnLeft = false;
        private boolean outsideCancelable = true;
        private OnAlertClickListener2 onAlertClickListener2;
        private OnAlertClickListener1 onAlertClickListener1;

        private boolean newStyle;


        private Context context;
        private int theme;

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }

        public Builder(Context context, int theme , boolean newStyle) {
            this.context = context;
            this.theme = theme;
            this.newStyle = newStyle;
        }

        public Builder setTitle(Object title) {
            this.title = title;
            return this;
        }

        public Builder setContent(Object content) {
            this.content = content;
            return this;
        }

        public Builder setPositiveButtonText(Object positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            return this;
        }

        public Builder setNegativeButtonText(Object negativeButton) {
            this.negativeButtonText = negativeButton;
            return this;
        }


        public Builder setShowPositiveButton(boolean showPositiveButton) {
            this.showPositiveButton = showPositiveButton;
            return this;
        }

        public Builder setShowNegativeButton(boolean showNegativeButton) {
            this.showNegativeButton = showNegativeButton;
            return this;
        }

        public Builder setShowCancelIcon(boolean showCancelIcon) {
            this.showCancelIcon = showCancelIcon;
            return this;
        }

        public Builder setPositiveOnLeft(boolean positiveOnLeft) {
            this.positiveOnLeft = positiveOnLeft;
            return this;
        }

        public Builder setOnAlertClickListener2(OnAlertClickListener2 onAlertClickListener2) {
            this.onAlertClickListener2 = onAlertClickListener2;
            return this;
        }

        public Builder setOnAlertClickListener1(OnAlertClickListener1 onAlertClickListener1) {
            this.onAlertClickListener1 = onAlertClickListener1;
            return this;
        }

        public Builder setOutsideCancelable(boolean outsideCancelable) {
            this.outsideCancelable = outsideCancelable;
            return this;
        }

        public CustomDialog build() {
            int layoutId;
            boolean oneButton = showPositiveButton != showNegativeButton;

//            if (newStyle){
//                layoutId = R.layout.custom_alert_two_button_new;
//            }else {
                if (oneButton) {
                    layoutId = R.layout.custom_alert_one_button_new;
                } else {
                    layoutId = R.layout.custom_alert_two_button_new;
                }
//            }
            View view = LayoutInflater.from(context).inflate(layoutId, null, false);

            final CustomDialog dialog = new CustomDialog(context, theme);
            dialog.addContentView(view, new ViewGroup.LayoutParams(ViewUtils.getScreenWidth(context) - ViewUtils.dip2px(context, 70), LayoutParams.WRAP_CONTENT));


            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            View ll_cancel = view.findViewById(R.id.ll_cancel);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);


            ViewUtils.setText(tv_title, title);
            ViewUtils.setText(tv_content, content);


            if (showCancelIcon) {
                ll_cancel.setVisibility(View.VISIBLE);
                ll_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            } else {
                ll_cancel.setVisibility(View.GONE);
            }


            if (oneButton) {
                Button bt = (Button) view.findViewById(R.id.bt);

                if (showPositiveButton) {
                    ViewUtils.setText(bt, positiveButtonText);
                } else {
                    ViewUtils.setText(bt, negativeButtonText);
                }

                if (onAlertClickListener1 != null) {
                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onAlertClickListener1.onPositiveClick(dialog);
                        }
                    });

                }


            } else {
                Button bt_positive = (Button) view.findViewById(R.id.bt_left);
                Button bt_negative = (Button) view.findViewById(R.id.bt_right);

                if (!positiveOnLeft) {
                    Button temp = bt_positive;
                    bt_positive = bt_negative;
                    bt_negative = temp;
                }
                ViewUtils.setText(bt_positive, positiveButtonText);
                ViewUtils.setText(bt_negative, negativeButtonText);

                bt_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAlertClickListener2.onPositiveClick(dialog);
                    }
                });

                bt_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAlertClickListener2.onNegativeClick(dialog);
                    }
                });

            }

            if (!outsideCancelable) {
                dialog.setCancelable(false);
            }


            return dialog;
        }


    }
}
