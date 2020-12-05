package com.huiboapp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.app.utils.SPUtils;
import com.huiboapp.di.component.DaggerAllLoanComponent;
import com.huiboapp.di.module.AllLoanModule;
import com.huiboapp.event.ClickEvent;
import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.AllLoanContract;
import com.huiboapp.mvp.model.entity.ParkListEntity;
import com.huiboapp.mvp.presenter.AllLoanPresenter;
import com.huiboapp.mvp.ui.widget.views.FindParkPopWindow;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


public class AllLoanProFragment extends MBaseFragment<AllLoanPresenter> implements AllLoanContract.View {


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
    LinearLayout container;

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
        mPresenter.findParkList();
        mMapView.onCreate(savedInstanceState);

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
        FindParkPopWindow findParkPopWindow = new FindParkPopWindow(getContext(), popView, mDatas);
        findParkPopWindow.showAtLocation(container, Gravity.BOTTOM, 0, getBottomHeight());
    }

    @Override
    public void setRecProduct(ParkListEntity parkListEntity, boolean isLoadmore) {
        mDatas = parkListEntity;
    }

    @Override
    public void onError() {

    }


}
