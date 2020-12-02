package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.SPUtils;
import com.huiboapp.di.component.DaggerSettingComponent;
import com.huiboapp.di.module.SettingModule;
import com.huiboapp.mvp.contract.SettingContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.presenter.SettingPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.DeviceUtils;

import butterknife.BindView;


/**
 * 今日有借设置
 */
public class SettingActivity extends MBaseActivity<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvLogout)
    TextView tvLogout;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_version)
    TextView mVersion;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;


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
        mVersion.setText(DeviceUtils.getVersionName(this));
        rlayoutTitle.setBackgroundColor(ContextCompat.getColor(this, R.color.app_bg));
        tvLogout.setOnClickListener(this);
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
        }
    }

    @Override
    public void logout() {
        SPUtils.getInstance().put(MyConstant.NET_ERROR, "-1");
        UserInfoHelper.getInstance().clearUserInfo();
        finish();
    }

}
