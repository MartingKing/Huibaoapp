package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerContactCustomerServiceComponent;
import com.huiboapp.di.module.ContactCustomerServiceModule;
import com.huiboapp.mvp.contract.ContactCustomerServiceContract;
import com.huiboapp.mvp.presenter.ContactCustomerServicePresenter;

import butterknife.BindView;

/**
 * 联系客服 todo 暂时无用的页面
 */
public class ContactCustomerServiceActivity extends MBaseActivity<ContactCustomerServicePresenter> implements ContactCustomerServiceContract.View {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerContactCustomerServiceComponent
                .builder()
                .appComponent(appComponent)
                .contactCustomerServiceModule(new ContactCustomerServiceModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_contact_customer_service;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isBarDarkFont = false;
        isBarBule = true;
        tvTitle.setText(getResources().getText(R.string.contact_survice));
        rlayoutTitle.setBackgroundColor(ContextCompat.getColor(this, R.color.app_status_bar));
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initViewClick(int id) {
        if (id == R.id.ivBack) {
            finish();
        }
    }
}
