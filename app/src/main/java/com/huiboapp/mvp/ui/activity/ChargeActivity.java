package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.ToastUtils;
import com.huiboapp.di.component.DaggerChargeComponent;
import com.huiboapp.di.module.ChargeModule;
import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.ChargeContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.ChargeEntity;
import com.huiboapp.mvp.presenter.ChargePresenter;
import com.huiboapp.mvp.ui.adapter.ChargeAdapter;
import com.jess.arms.di.component.AppComponent;
import com.tsy.sdk.pay.alipay.Alipay;
import com.tsy.sdk.pay.weixin.WXPay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ChargeActivity extends MBaseActivity<ChargePresenter> implements ChargeContract.View, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.amout1)
    RadioButton amout1;
    @BindView(R.id.amout2)
    RadioButton amout2;
    @BindView(R.id.amout3)
    RadioButton amout3;
    @BindView(R.id.tv_amount_group1)
    LinearLayout tvAmountGroup1;
    @BindView(R.id.amout4)
    RadioButton amout4;
    @BindView(R.id.amout5)
    RadioButton amout5;
    @BindView(R.id.amout6)
    RadioButton amout6;
    @BindView(R.id.tv_amount_group)
    LinearLayout tvAmountGroup;
    @BindView(R.id.inputamount)
    EditText inputamount;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.radiowx)
    RadioButton radiowx;
    @BindView(R.id.rlwexin)
    RelativeLayout rlwexin;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.radiozfb)
    RadioButton radiozfb;
    @BindView(R.id.rlzfb)
    RelativeLayout rlzfb;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private String model;
    private String[] sAmout = {"50", "100", "200", "300", "500", "1000"};
    private int chargeAmout;
    ChargeAdapter chargeAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChargeComponent
                .builder()
                .appComponent(appComponent)
                .chargeModule(new ChargeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_charge;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
//        initAdapter();
        tvTitle.setText("充值");
        initEvent();
    }

    private void initAdapter() {
        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        chargeAdapter = new ChargeAdapter(R.layout.item_charge);
        recyclerview.setAdapter(chargeAdapter);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < sAmout.length; i++) {
            data.add(i, sAmout[i]);
        }
        chargeAdapter.addData(data);
    }

    private void initEvent() {
        amout1.setOnCheckedChangeListener(this);
        amout2.setOnCheckedChangeListener(this);
        amout3.setOnCheckedChangeListener(this);
        amout4.setOnCheckedChangeListener(this);
        amout5.setOnCheckedChangeListener(this);
        amout6.setOnCheckedChangeListener(this);
        radiowx.setOnCheckedChangeListener(this);
        radiozfb.setOnCheckedChangeListener(this);
        btnLogin.setOnClickListener(this);
        rlwexin.setOnClickListener(this);
        rlzfb.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnLogin:
                if (!TextUtils.isEmpty(inputamount.getText().toString().trim())) {
                    chargeAmout = Integer.parseInt(inputamount.getText().toString().trim());
                }
                model = HBTUtls.getPayModel(radiowx.isChecked() ? 2 : 3);
                mPresenter.chargeMoney(chargeAmout, model);
                break;
            case R.id.rlwexin:
                radiowx.setChecked(true);
                radiozfb.setChecked(false);
                break;
            case R.id.rlzfb:
                radiozfb.setChecked(true);
                radiowx.setChecked(false);
                break;
        }
    }

    private void doWXPay(String pay_param) {
        WXPay.init(getApplicationContext(), MyConstant.WXAPP_ID);
        Log.e(TAG, "doWXPay: "+pay_param);
        WXPay.getInstance().doPay(pay_param, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                ToastUtils.showShort(ChargeActivity.this, String.valueOf(error_code));
                switch (error_code) {
                    case WXPay.NO_OR_LOW_WX:
                        Toast.makeText(getApplication(), "未安装微信或微信版本过低", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY_PARAM:
                        Toast.makeText(getApplication(), "参数错误", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 支付宝支付
     */
    private void doAlipay(String pay_param) {
        new Alipay(this, pay_param, new Alipay.AlipayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDealing() {
                Toast.makeText(getApplication(), "支付处理中...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case Alipay.ERROR_RESULT:
                        Toast.makeText(getApplication(), "支付失败:支付结果解析错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_NETWORK:
                        Toast.makeText(getApplication(), "支付失败:网络连接错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付错误:支付码支付失败", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(getApplication(), "支付错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        }).doPay();
    }

    @Override
    public void starPay(ChargeEntity chargeEntity) {
        ChargeEntity.DataBean data = chargeEntity.getData();
        Map<String, Object> params = new HashMap<>();
        params.put("appid", MyConstant.WXAPP_ID);
        params.put("partnerid", data.getPay_info().getPartnerid());
        params.put("prepayid", data.getPay_info().getPrepay_id());
        params.put("package", "Sign=WXPay");
        params.put("noncestr", data.getPay_info().getNonce_str());
        params.put("timestamp", data.getPay_info().getTimestamp());
        params.put("sign", data.getPay_info().getSign());
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String pay_param = gson.toJson(params);
        if (model.equals(HBTUtls.getPayModel(2))) {
            doWXPay(pay_param);
        }
        if (model.equals(HBTUtls.getPayModel(3))) {
            doAlipay(data.getPay_info().getOrder_info());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.amout1:
                if (isChecked) {
                    chargeAmout = 50;
                    amout2.setChecked(false);
                    amout3.setChecked(false);
                    amout4.setChecked(false);
                    amout5.setChecked(false);
                    amout6.setChecked(false);
                }
                break;
            case R.id.amout2:
                if (isChecked) {
                    chargeAmout = 100;
                    amout1.setChecked(false);
                    amout3.setChecked(false);
                    amout4.setChecked(false);
                    amout5.setChecked(false);
                    amout6.setChecked(false);
                }
                break;
            case R.id.amout3:
                if (isChecked) {
                    chargeAmout = 200;
                    amout2.setChecked(false);
                    amout1.setChecked(false);
                    amout4.setChecked(false);
                    amout5.setChecked(false);
                    amout6.setChecked(false);
                }
                break;
            case R.id.amout4:
                if (isChecked) {
                    chargeAmout = 300;
                    amout2.setChecked(false);
                    amout3.setChecked(false);
                    amout1.setChecked(false);
                    amout5.setChecked(false);
                    amout6.setChecked(false);
                }
                break;
            case R.id.amout5:
                if (isChecked) {
                    chargeAmout = 500;
                    amout2.setChecked(false);
                    amout3.setChecked(false);
                    amout4.setChecked(false);
                    amout1.setChecked(false);
                    amout6.setChecked(false);
                }
                break;
            case R.id.amout6:
                if (isChecked) {
                    chargeAmout = 1000;
                    amout2.setChecked(false);
                    amout3.setChecked(false);
                    amout4.setChecked(false);
                    amout5.setChecked(false);
                    amout1.setChecked(false);
                }
                break;
            case R.id.radiowx:
                if (isChecked) {
                    radiozfb.setChecked(false);
                }
                break;
            case R.id.radiozfb:
                if (isChecked) {
                    radiowx.setChecked(false);
                }
                break;
        }
    }
}
