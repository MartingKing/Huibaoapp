package com.huiboapp.app.utils;

import android.content.Context;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseObserver;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * 定时器
 * <p>
 * Created by yaojian on 2018/7/4 14:43
 */
public class TimerUtils {

    public static void smsTimer(Context context, TextView textView) {
        if (textView != null) {
            int count = 60;
            Observable observable = Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                    .take(count + 1) // 设置总共发送的次数
                    .compose(MBaseObserver.new_main());
            observable.subscribe(new DisposableObserver<Long>() {
                @Override
                public void onNext(Long aLong) {
                    if (context == null || textView == null) return;
                    textView.setEnabled(false);
                    textView.setTextColor(context.getResources().getColor(R.color.text_color_3));
                    textView.setText(String.format("%1$ds后重新获取", count - aLong));
                }

                @Override
                public void onError(Throwable e) {
                    if (context == null || textView == null) return;
                    textView.setEnabled(true);
                    textView.setTextColor(context.getResources().getColor(R.color.app_blue));
                    textView.setText("获取验证码");
                }

                @Override
                public void onComplete() {
                    if (context == null || textView == null) return;
                    textView.setEnabled(true);
                    textView.setTextColor(context.getResources().getColor(R.color.app_blue));
                    textView.setText("获取验证码");
                }
            });
        }
    }
}
