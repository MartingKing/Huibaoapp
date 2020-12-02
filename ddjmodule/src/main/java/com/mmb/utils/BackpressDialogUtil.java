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

public class BackpressDialogUtil extends Dialog implements View.OnClickListener {
    private TextView tv_content;
    private TextView go_setting;
    private TextView go_exit;

    private Context mContext;
    private OnCloseListener mListener;
    private boolean mCancelable;
    private String mContent;

    public BackpressDialogUtil(Context context, String content, boolean cancelable, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mListener = listener;
        this.mCancelable = cancelable;
        this.mContent = content;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backpress_dialog_layout);
        initView();
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_content.setText(mContent);
        go_setting = findViewById(R.id.go_setting);
        go_exit = findViewById(R.id.go_exit);
        go_setting.setOnClickListener(this);
        go_exit.setOnClickListener(this);

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
            }

        } else if (i == R.id.go_exit) {
            if (mListener != null) {
                this.dismiss();
                mListener.goExit();
            }

        }
    }

    public interface OnCloseListener {
        void goExit();
    }
}
