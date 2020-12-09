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
import com.huiboapp.di.component.DaggerTuikuanComponent;
import com.huiboapp.di.module.SettingModule;
import com.huiboapp.mvp.contract.SettingContract;
import com.huiboapp.mvp.presenter.SettingPresenter;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TuikuanActivity extends MBaseActivity<SettingPresenter> implements SettingContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTuikuanComponent
                .builder()
                .appComponent(appComponent)
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_tuikuan;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivBack.setOnClickListener(this);
        tvTitle.setText("退款");

    }

    @Override
    protected void initViewClick(int id) {
        switch (id) {
            case R.id.ivBack:
                finish();
                break;

        }
    }

    @Override
    public void logout() {

    }

}
