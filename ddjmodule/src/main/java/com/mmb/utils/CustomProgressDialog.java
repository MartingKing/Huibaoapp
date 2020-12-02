package com.mmb.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mmb.R;


/**
 * App下载
 *
 * Created by yaojian on 2019/3/13 12:27
 */
public class CustomProgressDialog extends Dialog {

    private ProgressBar mProgress;
    private TextView tvMsg;

    public CustomProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_progress);

        mProgress = findViewById(R.id.progress);
        tvMsg = findViewById(R.id.tvMsg);
    }

    public void setProgress(int value) {
        mProgress.setProgress(value);
        tvMsg.setText("App下载中... " + value + "%");
    }

    public int getProgress() {
        if (mProgress != null) {
            return mProgress.getProgress();
        }
        return 0;
    }

}
