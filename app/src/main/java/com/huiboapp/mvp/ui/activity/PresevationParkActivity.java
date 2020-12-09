package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerPresevationParkComponent;
import com.huiboapp.di.module.MyPresevationModule;
import com.huiboapp.mvp.contract.MyPresevationContract;
import com.huiboapp.mvp.presenter.MyPresevationPresenter;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;


public class PresevationParkActivity extends MBaseActivity<MyPresevationPresenter> implements MyPresevationContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    private String parksid;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPresevationParkComponent
                .builder()
                .appComponent(appComponent)
                .myPresevationModule(new MyPresevationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_presevation;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivBack.setOnClickListener(this);
        tvTitle.setText("我的预约");
        parksid = getIntent().getStringExtra("parksid");
        mPresenter.findOrderList(parksid);
    }

    @Override
    protected void initViewClick(int id) {
        switch (id) {
            case R.id.ivBack:
                finish();
                break;


        }
    }

}
