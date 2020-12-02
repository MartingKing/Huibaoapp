package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerDetectorInfoComponent;
import com.huiboapp.di.module.DetectorInfoModule;
import com.huiboapp.mvp.contract.DetectorInfoContract;
import com.huiboapp.mvp.presenter.DetectorInfoPresenter;

import butterknife.BindView;

/**
 * 探测器详情页 todo 暂时无用的页面
 */
public class DetectorInfoActivity extends MBaseActivity<DetectorInfoPresenter> implements DetectorInfoContract.View {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvQuota)
    TextView tvQuota;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDetectorInfoComponent
                .builder()
                .appComponent(appComponent)
                .detectorInfoModule(new DetectorInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_detector_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isBarDarkFont = false;

        ivBack.setOnClickListener(this);
        tvQuota.setOnClickListener(this);
    }

    @Override
    protected void initViewClick(int id) {
        if (id == R.id.ivBack) {
            finish();
        }
        if (id == R.id.tvQuota) {
            setIntent(RecommendProductActivity.class);
        }
    }
}
