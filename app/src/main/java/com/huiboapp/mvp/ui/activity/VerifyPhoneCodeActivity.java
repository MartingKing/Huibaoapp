package com.huiboapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.SpannableStringUtils;
import com.huiboapp.di.component.DaggerVerifyPhoneCodeComponent;
import com.huiboapp.di.module.VerifyPhoneCodeModule;
import com.huiboapp.mvp.contract.VerifyPhoneCodeContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.presenter.VerifyPhoneCodePresenter;
import com.huiboapp.mvp.ui.widget.views.VerifyEditText;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;


public class VerifyPhoneCodeActivity extends MBaseActivity<VerifyPhoneCodePresenter> implements VerifyPhoneCodeContract.View {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.verifycode)
    VerifyEditText verifycode;
    @BindView(R.id.tv_resendcode)
    TextView tvResendcode;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.btn_next_step)
    Button btnNextStep;
    private String mobile;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVerifyPhoneCodeComponent
                .builder()
                .appComponent(appComponent)
                .verifyPhoneCodeModule(new VerifyPhoneCodeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login_input_vfcode;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mobile = getIntent().getStringExtra(MyConstant.CONSTANT_PHONE);
        mPresenter.getPhoneCode(mobile);
        ivBack.setOnClickListener(this);
        btnNextStep.setOnClickListener(this);
        tvResendcode.setOnClickListener(this);
        tvDesc.setText(SpannableStringUtils.getBuilder("验证码已发送至手机:")
                .append(mobile)
                .setForegroundColor(ContextCompat.getColor(this, R.color.app_blue))
                .create());
    }


    @Override
    protected void initViewClick(int id) {
        super.initViewClick(id);
        switch (id) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tv_resendcode:
                mPresenter.getPhoneCode(mobile);
                break;
            case R.id.btn_next_step:
                String code = verifycode.getContent();
                startActivity(new Intent(VerifyPhoneCodeActivity.this, ResetPwdActivity.class).putExtra(MyConstant.CONSTANT_PHONE_CODE, code).putExtra(MyConstant.CONSTANT_PHONE, mobile));
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
