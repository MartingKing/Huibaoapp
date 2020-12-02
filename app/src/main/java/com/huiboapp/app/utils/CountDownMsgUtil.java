package com.huiboapp.app.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/6/15.
 * 倒计时工具类
 */

public class CountDownMsgUtil extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     * to {@link #start()} until the countdown is done and {@link #onFinish()}
     * is called.
     * @param countDownInterval The interval along the way to receive
     * {@link #onTick(long)} callbacks.
     */
    private TextView mView;

    public CountDownMsgUtil(long millisInFuture, long countDownInterval, TextView view) {
        super(millisInFuture, countDownInterval);
        this.mView = view;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mView != null) {
            mView.setClickable(false);
            mView.setText(String.valueOf(millisUntilFinished / 1000) + "s后重新获取");
        }
    }

    @Override
    public void onFinish() {
        if (mView != null) {
            mView.setClickable(true);
            mView.setText("发送验证码");
        }
    }
}
