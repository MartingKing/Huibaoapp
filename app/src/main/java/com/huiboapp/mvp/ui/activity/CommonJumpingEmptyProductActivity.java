package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.PjbTextUtils;
import com.huiboapp.R;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.huiboapp.mvp.ui.widget.dialog.DialogUtil;
import com.huiboapp.mvp.ui.widget.views.AlwaysMarqueeTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class CommonJumpingEmptyProductActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    TextView ivBack;
    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    @BindView(R.id.tvProductName)
    TextView tvProductName;
    @BindView(R.id.tv_profit_desc)
    TextView tvProfitDesc;
    @BindView(R.id.tv_sing1)
    TextView tvSing1;
    @BindView(R.id.tv_sing2)
    TextView tvSing2;
    @BindView(R.id.tv_amount)
    AlwaysMarqueeTextView tvAmount;
    @BindView(R.id.tv_month_profit)
    TextView tvMonthProfit;
    @BindView(R.id.tv_month_limit)
    TextView tvMonthLimit;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.applynow)
    TextView applynow;

    private ProductListEntity mData;
    private String[] mScores = new String[]{"76", "82", "88", "96"};


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_common_empty_pro;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar immersionBar = ImmersionBar.with(this).statusBarDarkFont(true);
        immersionBar.init();
        ivBack.setOnClickListener(v -> finish());
        applynow.setOnClickListener(v -> new DialogUtil(CommonJumpingEmptyProductActivity.this, true, R.style.dialog, "您的借款需求已成功提交基本资质已发起初审，若审核通过将有专业客服联系您！请您耐心等候。！", (dialog, confirm) -> {
            if (confirm) {
                dialog.dismiss();
                finish();
            } else {
                dialog.dismiss();
            }
        }).setPositiveButton("好的谢谢").show());
        int index = (int) (Math.random() * mScores.length);
        String score = mScores[index];
        tv_score.setText(score);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(MyConstant.HOME_EMPTY_JUMP)) {
                mData = (ProductListEntity) bundle.getSerializable(MyConstant.HOME_EMPTY_JUMP);
                Log.e(TAG, "initData: " + mData);
                if (mData != null) {
                    tvTitle.setText(mData.getProductName());
                    tvProductName.setText(mData.getProductName());
                    tvProfitDesc.setText(mData.getSubTitle());
                    Picasso.get()
                            .load(PjbTextUtils.getDefaultText(mData.getProductImg(), "http://m3.yinpiaobao.cn/daichao/dev/201903/20190328/153426223.png"))
                            .resize(50, 50)
                            .centerCrop()
                            .into(iv_pic);
                    tvAmount.setText(PjbTextUtils.getTwoPoint(Double.valueOf(PjbTextUtils.getDefaultText(mData.getMonthRate(), "0.03"))) + "%");
                    String sb = PjbTextUtils.getDefaultText(mData.getTermStart(), "1") + "-" + PjbTextUtils.getDefaultText(mData.getTermEnd(), "3") + "个月";
                    tvMonthLimit.setText(sb);
                    tvMonthProfit.setText(PjbTextUtils.getDefaultText(PjbTextUtils.getAmountByUnit(mData.getLoanMin()) + "-" + PjbTextUtils.getAmountByUnit(mData.getLoanMax()), "0.03%"));
                    if (!mData.getProductLabel().isEmpty()) {
                        if (mData.getProductLabel().contains(",")) {
                            String[] labelList = mData.getProductLabel().split(",");
                            List<Integer> mDatas = new ArrayList<>();
                            for (String aLabelList : labelList) {
                                if (Integer.valueOf(aLabelList) < 4)
                                    mDatas.add(Integer.valueOf(aLabelList));
                            }
                            if (mDatas.size() > 1) {
                                Collections.sort(mDatas);
                                tvSing1.setText(getSingleLable(String.valueOf(mDatas.get(0)), tvSing1));
                                tvSing2.setText(getSingleLable(String.valueOf(mDatas.get(1)), tvSing2));
                            } else {
                                tvSing1.setText(getSingleLable(String.valueOf(mDatas.get(0)), tvSing1));
                            }
                        } else {
                            if (!mData.getProductLabel().isEmpty()) {
                                tvSing1.setText(getSingleLable(mData.getProductLabel(), tvSing1));
                            }
                        }
                    }
                }
            }
        }
    }

    private String getSingleLable(String str, TextView view) {
        if (Integer.valueOf(str) < 4) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        String label = "";
        switch (str) {
            case "1":
                label = "低利息";
                break;
            case "2":
                label = "大额贷";
                break;
            case "3":
                label = "小额贷";
                break;
            default:
                break;
        }
        return label;
    }

}
