package com.huiboapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerSettingComponent;
import com.huiboapp.di.module.SettingModule;
import com.huiboapp.mvp.contract.SettingContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.presenter.SettingPresenter;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;


public class SettingActivity extends MBaseActivity<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.icon)
    TextView icon;
    @BindView(R.id.llchangepwd)
    LinearLayout llchangepwd;
    @BindView(R.id.icon1)
    TextView icon1;
    @BindView(R.id.llfeedback)
    LinearLayout llfeedback;
    @BindView(R.id.icon2)
    TextView icon2;
    @BindView(R.id.llagreement)
    LinearLayout llagreement;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.llayoutSetting)
    LinearLayout llayoutSetting;
    @BindView(R.id.tvLogout)
    TextView tvLogout;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent
                .builder()
                .appComponent(appComponent)
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivBack.setOnClickListener(this);
        tvTitle.setText(getText(R.string.setting));
        tvLogout.setOnClickListener(this);
        llchangepwd.setOnClickListener(this);
        llfeedback.setOnClickListener(this);
        llagreement.setOnClickListener(this);
    }

    @Override
    protected void initViewClick(int id) {
        switch (id) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvLogout:
                mPresenter.logout();
                break;
            case R.id.llchangepwd:
                startActivity(new Intent(SettingActivity.this, ResetPwdActivity.class).putExtra(MyConstant.CONSTANT_TAG, MyConstant.tagResetpwd));
                break;
            case R.id.llfeedback:
                startActivity(new Intent(SettingActivity.this, FeedbackActivity.class));
                break;
            case R.id.llagreement:
                startActivity(new Intent(SettingActivity.this, AgreementWebActivity.class));
                break;

        }
    }

    @Override
    public void logout() {
//        SPUtils.getInstance().put(MyConstant.NET_ERROR, "-1");
//        UserInfoHelper.getInstance().clearUserInfo();
//        finish();
    }

}
