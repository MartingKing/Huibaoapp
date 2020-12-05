package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerRecommendProductComponent;
import com.huiboapp.di.module.RecommendProductModule;
import com.huiboapp.mvp.contract.RecommendProductContract;
import com.huiboapp.mvp.presenter.RecommendProductPresenter;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;

/**
 * todo 暂时无用的页面
 */
public class RecommendProductActivity extends MBaseActivity<RecommendProductPresenter> implements RecommendProductContract.View {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvRecProductList)
    RecyclerView rvRecProductList;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRecommendProductComponent
                .builder()
                .appComponent(appComponent)
                .recommendProductModule(new RecommendProductModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recommend_product;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initViewClick(int id) {
        if (id == R.id.ivBack) {
            finish();
        }
    }

}
