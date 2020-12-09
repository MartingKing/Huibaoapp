package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerMyPresevationComponent;
import com.huiboapp.di.module.MyPresevationModule;
import com.huiboapp.mvp.contract.MyPresevationContract;
import com.huiboapp.mvp.presenter.MyPresevationPresenter;
import com.huiboapp.mvp.ui.adapter.MyPresevateAdapter;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MyPresevationActivity extends MBaseActivity<MyPresevationPresenter> implements MyPresevationContract.View {


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
    private String parksid;
    private MyPresevateAdapter presevateAdapter;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyPresevationComponent
                .builder()
                .appComponent(appComponent)
                .myPresevationModule(new MyPresevationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_presevation;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivBack.setOnClickListener(this);
        tvTitle.setText("我的预约");
        parksid = getIntent().getStringExtra("parksid");
        mPresenter.findOrderList(parksid);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        presevateAdapter = new MyPresevateAdapter();
        recyclerview.setAdapter(presevateAdapter);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(i,""+i);
        }
        presevateAdapter.addData(data);
    }

    @Override
    protected void initViewClick(int id) {
        switch (id) {
            case R.id.ivBack:
                finish();
                break;


        }
    }

}
