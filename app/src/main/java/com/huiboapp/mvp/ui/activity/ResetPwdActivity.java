package com.huiboapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonlib.agentweb.utils.AppUtils;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerResetPwdComponent;
import com.huiboapp.di.module.ResetPwdModule;
import com.huiboapp.mvp.contract.ResetPwdContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.presenter.ResetPwdPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;


public class ResetPwdActivity extends MBaseActivity<ResetPwdPresenter> implements ResetPwdContract.View {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd2)
    EditText etPwd2;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    private String vscode, mobile, intentTag;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerResetPwdComponent
                .builder()
                .appComponent(appComponent)
                .resetPwdModule(new ResetPwdModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_set_new_pwd;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        vscode = getIntent().getStringExtra(MyConstant.CONSTANT_PHONE_CODE);
        mobile = getIntent().getStringExtra(MyConstant.CONSTANT_PHONE);
        ivBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        intentTag = getIntent().getStringExtra(MyConstant.CONSTANT_TAG);
        if (intentTag.equals(MyConstant.tagRegist)) {
            tvTitle.setText("设置密码");
            tvDescribe.setText("设置登录密码");
        }
        if (intentTag.equals(MyConstant.tagResetpwd)) {
            tvTitle.setText("重置密码");
            tvDescribe.setText("设置新密码");
        }
    }


    @Override
    protected void initViewClick(int id) {
        super.initViewClick(id);
        switch (id) {
            case R.id.btnNext:
                String p1 = etPwd.getText().toString().trim();
                String p2 = etPwd2.getText().toString().trim();
                if (!p1.equals(p2)) {
                    ArmsUtils.makeText(AppUtils.getApp(), "密码不一致！");
                    return;
                }
                if (intentTag.equals(MyConstant.tagRegist)) {
                    mPresenter.initPwd(mobile, p1, vscode);
                }
                if (intentTag.equals(MyConstant.tagResetpwd)) {
                    mPresenter.resetPwd(mobile, p1, vscode);
                }
                break;
            case R.id.ivBack:
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onResetSuccess() {
        startActivity(new Intent(ResetPwdActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onResetFailue() {

    }

    @Override
    public void onRegistSuccess() {
        startActivity(new Intent(ResetPwdActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onRegistFailue() {

    }
}
