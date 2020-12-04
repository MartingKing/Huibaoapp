package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerMyCarsComponent;
import com.huiboapp.di.module.MyCarsModule;
import com.huiboapp.mvp.contract.MyCarsContract;
import com.huiboapp.mvp.presenter.MyCarsPresenter;
import com.huiboapp.mvp.ui.adapter.MyCarsAdapter;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyCarsActivity extends MBaseActivity<MyCarsPresenter> implements MyCarsContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private MyCarsAdapter myCarsAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyCarsComponent
                .builder()
                .appComponent(appComponent)
                .myCarsModule(new MyCarsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_mycars;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText("我的车辆");
        myCarsAdapter = new MyCarsAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(myCarsAdapter);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(i + "");
        }
        myCarsAdapter.addData(data);
    }


    @Override
    protected void initViewClick(int id) {
        super.initViewClick(id);
        switch (id) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnNext:

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
