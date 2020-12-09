package com.huiboapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.ToastUtils;
import com.huiboapp.di.component.DaggerFastPayComponent;
import com.huiboapp.di.module.FastPayModule;
import com.huiboapp.mvp.contract.FastPayContract;
import com.huiboapp.mvp.model.entity.HomeOrderEntity;
import com.huiboapp.mvp.model.entity.PayInfo;
import com.huiboapp.mvp.presenter.FastPayPresenter;
import com.huiboapp.mvp.ui.widget.views.SelectCityPopWindow;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FastPayActivity extends MBaseActivity<FastPayPresenter> implements FastPayContract.View, CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.radioBlue)
    RadioButton radioBlue;
    @BindView(R.id.radioYello)
    RadioButton radioYello;
    @BindView(R.id.radioGreen)
    RadioButton radioGreen;
    @BindView(R.id.evCarNumber)
    EditText evCarNumber;
    @BindView(R.id.charge)
    Button charge;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.tv_city)
    TextView tvCity;
    private String selectColor = "blue";

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tv_city:
                initSelectWindow();
                break;
            case R.id.charge:
                String plate = tvCity.getText().toString().trim() + evCarNumber.getText().toString().trim();
                if (plate.length() < 7 || TextUtils.isEmpty(plate)) {
                    ToastUtils.showShort(this, "请输入正确车牌号!");
                    return;
                }
                if (selectColor.equals("green") && plate.length() < 8) {
                    ToastUtils.showShort(this, "选择的车型和车牌号不符!");
                    return;
                }
                mPresenter.fastPay(plate, selectColor);
                break;
        }
    }

    private void initSelectWindow() {
        View popView = getLayoutInflater().inflate(R.layout.layout_select_city, null);
        SelectCityPopWindow selectColorWindow = new SelectCityPopWindow(this, popView);
        selectColorWindow.showAtLocation(container, Gravity.CENTER, 0, 0);
        selectColorWindow.setClickListerner(new SelectCityPopWindow.SelectCityListener() {
            @Override
            public void onClick(String city) {
                tvCity.setText(city);
                selectColorWindow.dismiss();
            }
        });
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        tvTitle.setText("快捷缴费");
        ivBack.setOnClickListener(this);
        charge.setOnClickListener(this);
        tvCity.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.radioBlue:
                if (isChecked)
                    selectColor = "blue";
                break;
            case R.id.radioGreen:
                if (isChecked)
                    selectColor = "green";
                break;
            case R.id.radioYello:
                if (isChecked)
                    selectColor = "yellow";
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFastPayComponent
                .builder()
                .appComponent(appComponent)
                .fastPayModule(new FastPayModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fastpay;
    }

    private List<PayInfo> payInfos = new ArrayList<>();

    @Override
    public void onderDetail(HomeOrderEntity commonEntity) {
        HomeOrderEntity.DataBean.OrderlistBean orderInfo = commonEntity.getData().getOrderlist().get(0);
        PayInfo payInfo = new PayInfo();
        payInfo.setId(orderInfo.getId());
        payInfo.setPayamout(orderInfo.getTotalfee());
        payInfos.add(payInfo);
        String jsonData = new Gson().toJson(payInfos);
        startActivity(new Intent(FastPayActivity.this, PayOrderActivity.class)
                .putExtra("shouldPayFee", orderInfo.getTotalfee())
                .putExtra("jsonData", jsonData)
                .putExtra("parkAddr", orderInfo.getParksname()));
    }

}
