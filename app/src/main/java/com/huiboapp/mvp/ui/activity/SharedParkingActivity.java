package com.huiboapp.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.poisearch.PoiSearch;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerSharedParkingComponent;
import com.huiboapp.di.module.SharedParkingModule;
import com.huiboapp.event.ParkInfoEvent;
import com.huiboapp.mvp.contract.SharedParkingContract;
import com.huiboapp.mvp.model.entity.ParkListEntity.DataBean.ResourcelistBean;
import com.huiboapp.mvp.presenter.SharedParkingPresenter;
import com.huiboapp.mvp.ui.widget.views.DirectPopWindow;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


public class SharedParkingActivity extends MBaseActivity<SharedParkingPresenter> implements SharedParkingContract.View, LocationSource,
        AMapLocationListener {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.keyWord)
    TextView keyWord;
    @BindView(R.id.mypre)
    TextView mypre;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.llsearch)
    LinearLayout llsearch;
    private AMap aMap;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private DirectPopWindow directPopWindow;
    private boolean isFirst = true;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private String parksid;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSharedParkingComponent
                .builder()
                .appComponent(appComponent)
                .sharedParkingModule(new SharedParkingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sharedpark;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText("共享停车");
        map.onCreate(savedInstanceState);
        setUpMap();
        initEvent();
    }

    private void setUpMap() {
        aMap = map.getMap();
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        mLocationOption.setNeedAddress(true);
        aMap.setMyLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location));
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
    }

    private void initPop(ResourcelistBean data) {
        parksid = data.getParksid();
        View popView = getLayoutInflater().inflate(R.layout.layout_location_direct, null);
        directPopWindow = new DirectPopWindow(SharedParkingActivity.this, popView, data,"预约泊位");
        directPopWindow.showAtLocation(container, Gravity.BOTTOM, 0, 0);
        directPopWindow.setDirectPopWindowClickListerner(new DirectPopWindow.DirectPopWindowClickListerner() {
            @Override
            public void onShareClick() {

            }

            @Override
            public void onDirectClick() {
                // TODO: 2020/12/8
            }
        });
    }

    private void initEvent() {
        ivBack.setOnClickListener(this);
        mypre.setOnClickListener(this);
        llsearch.setOnClickListener(this);
        keyWord.setOnClickListener(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(ParkInfoEvent event) {
        initPop(event.getResourcelistBean());
    }
    @Override
    protected void initViewClick(int id) {
        switch (id) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.llsearch:
            case R.id.keyWord:
                startActivity(new Intent(SharedParkingActivity.this, SearchParkActivity.class));
                break;
            case R.id.mypre:
                startActivity(new Intent(SharedParkingActivity.this, MyPresevationActivity.class).putExtra("parksid",parksid));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
//            setCustomMarker(amapLocation);
            Log.e(TAG, "onLocationChanged1: " + amapLocation.getAddress());
            if (!TextUtils.isEmpty(amapLocation.getLocationDetail())) {
            }
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e(TAG, "onLocationChanged2: " + errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

}
