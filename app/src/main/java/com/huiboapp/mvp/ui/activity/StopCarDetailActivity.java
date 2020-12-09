package com.huiboapp.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.google.gson.Gson;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerStopCarDetailComponent;
import com.huiboapp.di.module.StopCarDetailModule;
import com.huiboapp.mvp.contract.StopCarDetailContract;
import com.huiboapp.mvp.model.entity.OrderDetailInfo;
import com.huiboapp.mvp.model.entity.PayEntity;
import com.huiboapp.mvp.model.entity.PayInfo;
import com.huiboapp.mvp.presenter.StopCarDetailPresenter;
import com.huiboapp.mvp.ui.widget.views.OrderDetailPopWindow;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class StopCarDetailActivity extends MBaseActivity<StopCarDetailPresenter> implements StopCarDetailContract.View {


    String orderid;
    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.llcontainer)
    LinearLayout container;
    private AMap aMap;
    private boolean isFirst = true;

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
        aMap = mMapView.getMap();
        setUpMap();
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location));
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst && orderDetailPopWindow != null) {
            orderDetailPopWindow.showAtLocation(container, Gravity.BOTTOM, 0, 0);
            isFirst = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        orderDetailPopWindow.dismiss();
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);

    }

    private List<PayInfo> payInfos = new ArrayList<>();
    private OrderDetailInfo detailInfo;
    private String shouldPayFee = "0.0";
    private String parkAddr = "";
    private OrderDetailPopWindow orderDetailPopWindow;

    @Override
    public void onderDetail(OrderDetailInfo orderDetailInfo) {
        detailInfo = orderDetailInfo;
        View popView = getLayoutInflater().inflate(R.layout.layout_orderdetail, null);
        orderDetailPopWindow = new OrderDetailPopWindow(this, popView, detailInfo);
        showLocation(Double.parseDouble(detailInfo.getData().getLat()), Double.parseDouble(detailInfo.getData().getLng()));
        shouldPayFee = detailInfo.getData().getUnpaidfee() + "";
        parkAddr = detailInfo.getData().getParksname();
        orderDetailPopWindow.setPayListener(new OrderDetailPopWindow.OnPayListener() {
            @Override
            public void onPay() {
                PayInfo payInfo = new PayInfo();
                payInfo.setId(detailInfo.getData().getId());
                payInfo.setPayamout(detailInfo.getData().getTotalfee());
                payInfos.add(payInfo);
                String jsonData = new Gson().toJson(payInfos);
                startActivity(new Intent(StopCarDetailActivity.this, PayOrderActivity.class)
                        .putExtra("shouldPayFee", shouldPayFee)
                        .putExtra("jsonData", jsonData)
                        .putExtra("parkAddr", parkAddr));
            }
        });
    }

    @Override
    public void starPay(PayEntity commonEntity) {

    }

    //移动到指定经纬度
    private void showLocation(double latitude, double longitude) {
        CameraPosition cameraPosition = new CameraPosition(new LatLng(latitude, longitude), 15, 0, 30);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        aMap.moveCamera(cameraUpdate);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location))
                .draggable(true);
        Marker marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }

}
