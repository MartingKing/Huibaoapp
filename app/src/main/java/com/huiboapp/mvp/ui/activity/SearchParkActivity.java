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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerSParkComponent;
import com.huiboapp.di.module.SParkModule;
import com.huiboapp.event.ParkInfoEvent;
import com.huiboapp.mvp.contract.SParkContract;
import com.huiboapp.mvp.model.entity.ParkListEntity;
import com.huiboapp.mvp.presenter.SParkPresenter;
import com.huiboapp.mvp.ui.adapter.FindParkAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.EventBusHelper;

import butterknife.BindView;


public class SearchParkActivity extends MBaseActivity<SParkPresenter> implements SParkContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.tvsearch)
    TextView tvsearch;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    FindParkAdapter findParkAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSParkComponent
                .builder()
                .appComponent(appComponent)
                .sParkModule(new SParkModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_searchpark;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivBack.setOnClickListener(this);
        tvTitle.setText("搜索停车场");
        mPresenter.parkinfo();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        findParkAdapter = new FindParkAdapter(R.layout.item_findpark);
        recyclerview.setAdapter(findParkAdapter);

    }

    @Override
    protected void initViewClick(int id) {
        switch (id) {
            case R.id.ivBack:
                finish();
                break;

        }
    }


    @Override
    public void parkinfo(ParkListEntity commonEntity) {
        findParkAdapter.addData(commonEntity.getData().getResourcelist());
        findParkAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ParkListEntity.DataBean.ResourcelistBean resourcelistBean = commonEntity.getData().getResourcelist().get(position);
                EventBusHelper.postStickyEvent(new ParkInfoEvent(resourcelistBean));
                finish();
            }
        });
    }
}
