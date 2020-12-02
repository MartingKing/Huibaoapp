package com.mmb.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mmb.R;


/**
 * Created by Administrator on 2018/6/11.
 */

public class AuthrizationDialogUtil extends Dialog implements View.OnClickListener {
    private TextView tv_content;
    private TextView go_setting;

    private Context mContext;
    private OnCloseListener mListener;
    private boolean mCancelable;
    private String mContent;

    public AuthrizationDialogUtil(Context context, String content, boolean cancelable, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mListener = listener;
        this.mCancelable = cancelable;
        this.mContent = content;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_dialog_layout);
        initView();
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_content.setText(mContent);
        go_setting = findViewById(R.id.go_setting);
        go_setting.setOnClickListener(this);

        if (!mCancelable) {
            this.setCancelable(false);
            this.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.go_setting) {
            if (mListener != null) {
                this.dismiss();
                mListener.goSetting();
            }

        } else {
        }
    }

    public interface OnCloseListener {
        void goSetting();
    }
}
