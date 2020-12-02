package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerRegistAgreementComponent;
import com.huiboapp.di.module.RegistAgreementModule;
import com.huiboapp.mvp.contract.RegistAgreementContract;
import com.huiboapp.mvp.presenter.RegistAgreementPresenter;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;


/**
 * 注册协议
 */
public class RegistAgreementActivity extends MBaseActivity<RegistAgreementPresenter> implements RegistAgreementContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegistAgreementComponent
                .builder()
                .appComponent(appComponent)
                .registAgreementModule(new RegistAgreementModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_regist_agreement;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText(getResources().getText(R.string.register_agreement));
        rlayoutTitle.setBackgroundColor(ContextCompat.getColor(this, R.color.app_status_bar));
        ivBack.setOnClickListener(view -> finish());
        webview.loadUrl("file:////android_asset/register_aggrement.html");
        isBarDarkFont = false;
        isBarBule = true;
    }

}
