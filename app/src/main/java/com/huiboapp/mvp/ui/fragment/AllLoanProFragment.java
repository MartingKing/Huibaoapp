package com.huiboapp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.app.utils.SPUtils;
import com.huiboapp.di.component.DaggerAllLoanComponent;
import com.huiboapp.di.module.AllLoanModule;
import com.huiboapp.mvp.contract.AllLoanContract;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.huiboapp.mvp.presenter.AllLoanPresenter;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

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
        mMapView.onResume();
        SPUtils.getInstance().clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("找泊位");
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

    @Override
    public void onError() {

    }

    @Override
    public void setRecProduct(List<ProductListEntity> products, boolean isLoadmore) {

    }

}
