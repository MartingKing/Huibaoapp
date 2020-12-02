package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerRegistComponent;
import com.huiboapp.di.module.RegistModule;
import com.huiboapp.mvp.contract.RegistContract;
import com.huiboapp.mvp.presenter.RegistPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;


public class RegistActivity extends MBaseActivity<RegistPresenter> implements RegistContract.View {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btnNext)
    Button btnNext;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegistComponent
                .builder()
                .appComponent(appComponent)
                .registModule(new RegistModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_regist;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private boolean checkPwd(String pwd) {
        if (pwd.length() < 6) {
            ArmsUtils.makeText(this, "请输入6-16位的密码！");
            return false;
        }
        return true;
    }

    @Override
    protected void initViewClick(int id) {
        super.initViewClick(id);
        switch (id) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnNext:
                String phone = etPhone.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailue() {

    }

}
