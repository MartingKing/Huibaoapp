package com.huiboapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerWelcomeComponent;
import com.huiboapp.di.module.WelcomeModule;
import com.huiboapp.mvp.contract.WelcomeContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.presenter.WelcomePresenter;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;

/**
 * 欢迎页
 */
public class WelcomeActivity extends MBaseActivity<WelcomePresenter> implements WelcomeContract.View {


    @BindView(R.id.llayoutSplash)
    ImageView llayoutSplash;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWelcomeComponent
                .builder()
                .appComponent(appComponent)
                .welcomeModule(new WelcomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isBarDarkFont = false;
        initImmersionBar();
        //将window的背景图设置为空
        getWindow().setBackgroundDrawable(null);
        mPresenter.getSplash();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(UserInfoHelper.getInstance().getToken())){
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 500);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getSplashUrl(String url) {
        Glide.with(this).load(url).into(llayoutSplash);
    }

    @Override
    public void onFailed() {

    }

}
