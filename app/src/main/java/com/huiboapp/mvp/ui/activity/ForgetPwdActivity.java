package com.huiboapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.model.constant.MyConstant;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ForgetPwdActivity extends AppCompatActivity {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.btn_next_step)
    Button btnNextStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pone);
        ButterKnife.bind(this);
        tvTitle.setText("重置密码");
        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pohne = etUsername.getText().toString().trim();
                if (HBTUtls.checkInput(pohne)) {
                    startActivity(new Intent(ForgetPwdActivity.this, VerifyPhoneCodeActivity.class).putExtra(MyConstant.CONSTANT_PHONE, pohne).putExtra(MyConstant.CONSTANT_TAG, MyConstant.tagResetpwd));
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
