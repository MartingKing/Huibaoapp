package com.mmb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.mmb.DdjMainActivity;
import com.mmb.R;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    private static final int GOTOMAIN = 560;
    private static final String TAG = "SplashActivity";

    private static class InnerHandler extends Handler {
        private final WeakReference<SplashActivity> mActivity;

        public InnerHandler(SplashActivity activity) {
            mActivity = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == GOTOMAIN) {
                    activity.startActivity(new Intent(activity, DdjMainActivity.class));
                    activity.finish();
                }
            }
        }
    }

    private final InnerHandler mHandler = new InnerHandler(this);
    private final Runnable sRunnable = new Runnable() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = GOTOMAIN;
            mHandler.sendMessage(message);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(sRunnable, 2000);
    }
}
