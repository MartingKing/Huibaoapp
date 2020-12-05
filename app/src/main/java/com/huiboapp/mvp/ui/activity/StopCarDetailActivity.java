package com.huiboapp.mvp.ui.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerStopCarDetailComponent;
import com.huiboapp.di.module.StopCarDetailModule;
import com.huiboapp.mvp.contract.StopCarDetailContract;
import com.huiboapp.mvp.model.entity.OrderDetailInfo;
import com.huiboapp.mvp.presenter.StopCarDetailPresenter;
import com.huiboapp.mvp.ui.widget.views.OrderDetailPopWindow;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;


public class StopCarDetailActivity extends MBaseActivity<StopCarDetailPresenter> implements StopCarDetailContract.View, AMap.OnMyLocationChangeListener {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.llcontainer)
    LinearLayout container;

    String orderid;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStopCarDetailComponent
                .builder()
                .appComponent(appComponent)
                .stopCarDetailModule(new StopCarDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_stopcar;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isBarDarkFont = false;
        tvTitle.setText("停车明细");
        orderid = getIntent().getStringExtra("orderid");
        mPresenter.getOrderDetail(orderid);
        ivBack.setOnClickListener(this);
        mMapView.onCreate(savedInstanceState);
        AMap aMap = mMapView.getMap();

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
    protected void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null)
            mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMyLocationChange(Location location) {
        //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取（获取地址描述数据章节有介绍

    }

    @Override
    public void onderDetail(OrderDetailInfo orderDetailInfo) {
        View popView = getLayoutInflater().inflate(R.layout.layout_orderdetail, null);
        OrderDetailPopWindow orderDetailPopWindow = new OrderDetailPopWindow(this, popView, orderDetailInfo);
        orderDetailPopWindow.showAtLocation(container, Gravity.BOTTOM, 0, 0);
    }
}
