package com.huiboapp.mvp.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.RouteSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.app.utils.SPUtils;
import com.huiboapp.app.utils.ToastUtils;
import com.huiboapp.di.component.DaggerAllLoanComponent;
import com.huiboapp.di.module.AllLoanModule;
import com.huiboapp.event.ClickEvent;
import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.common.LocationManager;
import com.huiboapp.mvp.contract.AllLoanContract;
import com.huiboapp.mvp.model.entity.ParkListEntity;
import com.huiboapp.mvp.presenter.AllLoanPresenter;
import com.huiboapp.mvp.ui.widget.views.DirectPopWindow;
import com.huiboapp.mvp.ui.widget.views.FindParkPopWindow;
import com.huiboapp.mvp.ui.widget.views.PoiOverlay;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;


public class AllLoanProFragment extends MBaseFragment<AllLoanPresenter> implements AllLoanContract.View, LocationSource,
        AMapLocationListener, PoiSearch.OnPoiSearchListener {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.keyWord)
    EditText keyWord;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private FindParkPopWindow findParkPopWindow;
    private DirectPopWindow directPopWindow;
    private RouteSearch mRouteSearch;
    private LocationManager locationManager;
    private LatLonPoint currentLat;

    public static AllLoanProFragment newInstance() {
        return new AllLoanProFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAllLoanComponent
                .builder()
                .appComponent(appComponent)
                .allLoanModule(new AllLoanModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null)
            mMapView.onResume();
        SPUtils.getInstance().clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("找泊位");
        locationManager = new LocationManager();
        mPresenter.findParkList();
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        setUpMap();
        keyWord.setImeOptions(EditorInfo.IME_ACTION_DONE);
        keyWord.setSingleLine();
        keyWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    doSearchQuery();
                    return true;
                }
                return false;
            }
        });

    }

    protected void doSearchQuery() {
        showLoading();
        query = new PoiSearch.Query(keyWord.getText().toString(), "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);// 设置查第一页

        poiSearch = new PoiSearch(getContext(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        mRouteSearch = new RouteSearch(getContext());
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

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

        }
    }

    public int getBottomHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        int navigationHeight = getResources().getDimensionPixelSize(resourceId);
        int statusHeight = 0;
        if (HBTUtls.isNavigationBarShow(getActivity().getWindowManager())) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                String heightStr = clazz.getField("navigation_bar_height").get(object).toString();
                int height = Integer.parseInt(heightStr);
                //dp--->px
                statusHeight = getActivity().getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return navigationHeight + statusHeight;
    }

    private ParkListEntity mDatas;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(ClickEvent event) {
        View popView = getLayoutInflater().inflate(R.layout.layout_findpark, null);
        findParkPopWindow = new FindParkPopWindow(getContext(), popView, mDatas);
        HBTUtls.setPopupWindowTouchModal(findParkPopWindow, false);
        findParkPopWindow.showAtLocation(container, Gravity.BOTTOM, 0, getBottomHeight());

        findParkPopWindow.setClickListerner(new FindParkPopWindow.ParkPopWindowItemClickListerner() {
            @Override
            public void onClick(BaseQuickAdapter adapter, View view, int position) {
                View popView = getLayoutInflater().inflate(R.layout.layout_location_direct, null);
                ParkListEntity.DataBean.ResourcelistBean resourcelistBean = mDatas.getData().getResourcelist().get(position);
                directPopWindow = new DirectPopWindow(getContext(), popView, resourcelistBean,"导航泊车");
                HBTUtls.setPopupWindowTouchModal(directPopWindow, false);
                directPopWindow.showAtLocation(container, Gravity.BOTTOM, 0, getBottomHeight());
                directPopWindow.setDirectPopWindowClickListerner(new DirectPopWindow.DirectPopWindowClickListerner() {
                    @Override
                    public void onShareClick() {
                        ToastUtils.showShort(getContext(), "share");
                    }

                    @Override
                    public void onDirectClick() {
                        Poi end = new Poi(resourcelistBean.getAddress(), new LatLng(Double.parseDouble(resourcelistBean.getGeolocation().getLat()), Double.parseDouble(resourcelistBean.getGeolocation().getLng())), "");
                        locationManager.startLocation(getActivity());
                        locationManager.setLocationCallBack(new LocationManager.ILocationCallBack() {
                            @Override
                            public void callBack(String str, double lat, double lgt, AMapLocation aMapLocation) {
                                Poi start = new Poi(str, new LatLng(lat, lgt), "");
                                AmapNaviPage.getInstance().showRouteActivity(getContext(), new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), null);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void setRecProduct(ParkListEntity parkListEntity, boolean isLoadmore) {
        mDatas = parkListEntity;
    }

    @Override
    public void onError() {

    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            currentLat = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
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

    private void setCustomMarker(AMapLocation amapLocation) {
        LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
        MarkerOptions markerOption = new MarkerOptions()
                .anchor(0.5f, 0.5f)//定义marker 图标的锚点。
                .position(latLng).title("")//设置 Marker 的标题
                .snippet(amapLocation.getAddress())//设置 Marker 上的 snippet即文字片段
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location))//设置MarkerOptions 对象的自定义图标
                .draggable(true)//设置标记是否可拖动。
                .period(50);//设置多少帧刷新一次图片资源，Marker动画的间隔时间，值越小动画越快
        aMap.addMarker(markerOption);
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getContext());
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

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        hideLoading();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = result
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        ToastUtils.showShort(getContext(), "没搜到结果");
                    }
                }
            } else {
                ToastUtils.showShort(getContext(), "没搜到结果");
            }
        } else {
            ToastUtils.showShort(getContext(), rCode);
        }
    }

    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtils.showShort(getContext(), infomation);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
