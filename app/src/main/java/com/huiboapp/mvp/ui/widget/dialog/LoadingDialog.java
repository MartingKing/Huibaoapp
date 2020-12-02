package com.huiboapp.mvp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.TextView;

import com.huiboapp.R;

/**
 * 等待层
 *
 * Created by yaojian on 2018/3/29 14:54
 */

public class LoadingDialog extends Dialog {

    private TextView tv_msg;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        tv_msg = findViewById(R.id.tv_msg);
    }

    public void setMsg(String msg){
        tv_msg.setText(msg);
    }

    @Override
    public void onBackPressed() {}

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
