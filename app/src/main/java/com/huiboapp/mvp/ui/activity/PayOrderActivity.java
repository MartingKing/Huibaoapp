package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.ToastUtils;
import com.huiboapp.di.component.DaggerPayOrderComponent;
import com.huiboapp.di.module.StopCarDetailModule;
import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.StopCarDetailContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.OrderDetailInfo;
import com.huiboapp.mvp.model.entity.PayEntity;
import com.huiboapp.mvp.presenter.StopCarDetailPresenter;
import com.jess.arms.di.component.AppComponent;
import com.tsy.sdk.pay.alipay.Alipay;
import com.tsy.sdk.pay.weixin.WXPay;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class PayOrderActivity extends MBaseActivity<StopCarDetailPresenter> implements StopCarDetailContract.View {

    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.addr)
    TextView addr;
    @BindView(R.id.reset)
    ImageView reset;
    @BindView(R.id.radioRest)
    RadioButton radioRest;
    @BindView(R.id.payRest)
    RelativeLayout payRest;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.radioWx)
    RadioButton radioWx;
    @BindView(R.id.payWx)
    RelativeLayout payWx;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.radioZfb)
    RadioButton radioZfb;
    @BindView(R.id.payZfb)
    RelativeLayout payZfb;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private String jsonData, parkAddr, shouldPayFee, model = HBTUtls.getPayModel(1);

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPayOrderComponent
                .builder()
                .appComponent(appComponent)
                .stopCarDetailModule(new StopCarDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_layout_pay;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isBarDarkFont = false;
        tvTitle.setText("支付订单");

        initData();
        initEvent();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnLogin:
                mPresenter.pay(jsonData, shouldPayFee, model);
                break;
            case R.id.payRest:
                radioRest.setChecked(true);
                radioWx.setChecked(false);
                radioZfb.setChecked(false);
                break;
            case R.id.payWx:
                radioRest.setChecked(false);
                radioWx.setChecked(true);
                radioZfb.setChecked(false);
                break;
            case R.id.payZfb:
                radioRest.setChecked(false);
                radioWx.setChecked(false);
                radioZfb.setChecked(true);
                break;
        }
    }

    private void initEvent() {
        ivBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        payRest.setOnClickListener(this);
        payWx.setOnClickListener(this);
        payZfb.setOnClickListener(this);
        radioRest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    model = HBTUtls.getPayModel(1);
                    radioWx.setChecked(false);
                    radioWx.setChecked(false);
                }
            }
        });
        radioWx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    model = HBTUtls.getPayModel(2);
                    radioRest.setChecked(false);
                    radioZfb.setChecked(false);
                }
            }
        });
        radioZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    model = HBTUtls.getPayModel(3);
                    radioRest.setChecked(false);
                    radioWx.setChecked(false);
                }
            }
        });
    }

    private void initData() {
        shouldPayFee = getIntent().getStringExtra("shouldPayFee");
        parkAddr = getIntent().getStringExtra("parkAddr");
        jsonData = getIntent().getStringExtra("jsonData");
        addr.setText(parkAddr);
        content.setText(shouldPayFee);
    }

    private void doWXPay(String pay_param) {
        WXPay.init(getApplicationContext(), MyConstant.WXAPP_ID);
        WXPay.getInstance().doPay(pay_param, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                ToastUtils.showShort(PayOrderActivity.this, String.valueOf(error_code));
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
    public void onderDetail(OrderDetailInfo commonEntity) {

    }

    @Override
    public void starPay(PayEntity payEntity) {
        if (payEntity.getData() != null) {
            PayEntity.DataBean data = payEntity.getData();
            Map<String, Object> params = new HashMap<>();
            params.put("appid", MyConstant.WXAPP_ID);
            params.put("partnerid", data.getPay_info().getPartnerid());
            params.put("prepayid", data.getPay_info().getPrepay_id());
            params.put("package", "Sign=WXPay");
            params.put("noncestr", data.getPay_info().getNonce_str());
            params.put("timestamp", data.getPay_info().getTimestamp());
            params.put("sign", data.getPay_info().getSign());
            String pay_param = new Gson().toJson(params);
            if (model.equals(HBTUtls.getPayModel(2))) {
                doWXPay(pay_param);
            }
            if (model.equals(HBTUtls.getPayModel(3))) {
                doAlipay(payEntity.getData().getPay_info().getOrder_info());
            }
        }
    }

}
