package com.huiboapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerLoginComponent;
import com.huiboapp.di.module.LoginModule;
import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.LoginContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.presenter.LoginPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;


public class LoginActivity extends MBaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.refresh)
    ImageView refresh;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tv_forgertpwd)
    TextView tvForgertpwd;
    @BindView(R.id.register)
    TextView register;
    private String mobile;
    private String pwd;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mobile = getIntent().getStringExtra(MyConstant.CONSTANT_PHONE);
        btnLogin.setOnClickListener(this);
        tvForgertpwd.setOnClickListener(this);
        register.setOnClickListener(this);
        refresh.setOnClickListener(this);
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
            case R.id.btnLogin:
                mobile = etUsername.getText().toString().trim();
                pwd = etCode.getText().toString().trim();
                if (HBTUtls.checkInput(mobile) && checkPwd(pwd)) {
                    mPresenter.quickLogin(mobile, pwd);
                }
                break;
            case R.id.tv_forgertpwd:
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, NewRegistActivity.class));
                break;
            case R.id.refresh:

                break;
        }
    }

    @Override
    public void getPhoneVerifyCode(String phone, String imageCode) {

    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
