package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

public class CommonJumpingEmptyProductActivity extends BaseActivity {


    private ProductListEntity mData;


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

    }



}
