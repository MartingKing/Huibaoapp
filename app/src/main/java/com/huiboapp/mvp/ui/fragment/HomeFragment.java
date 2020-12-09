package com.huiboapp.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.commonlib.agentweb.utils.AppUtils;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.di.component.DaggerHomeComponent;
import com.huiboapp.di.module.HomeModule;
import com.huiboapp.mvp.contract.HomeContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.HomeOrderEntity;
import com.huiboapp.mvp.model.entity.UserInfoEntity;
import com.huiboapp.mvp.presenter.HomePresenter;
import com.huiboapp.mvp.ui.activity.AddCarActivity;
import com.huiboapp.mvp.ui.activity.FastPayActivity;
import com.huiboapp.mvp.ui.activity.ParkedHistoryActivity;
import com.huiboapp.mvp.ui.activity.SharedParkingActivity;
import com.huiboapp.mvp.ui.adapter.HomeOrderAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.EventBusHelper;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class HomeFragment extends MBaseFragment<HomePresenter> implements HomeContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;
    @BindView(R.id.banner)
    ConvenientBanner mBanner;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.rl_share_park)
    RelativeLayout rlSharePark;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.rl_park_record)
    RelativeLayout rlParkRecord;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.rl_fast_pay)
    RelativeLayout rlFastPay;
    @BindView(R.id.icon_layout)
    LinearLayout iconLayout;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.rvProductList)
    RecyclerView rvProductList;
    private HomeOrderAdapter homeOrderAdapter;
    private List<UserInfoEntity.CarList> platelist;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //添加自定义分割线
        ivBack.setVisibility(View.GONE);
        mPresenter.getMemberInfo();
        mPresenter.getOrderInfo();
        tvTitle.setText(R.string.app_name);
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.item_divider1)));
        rvProductList.addItemDecoration(divider);
        rvProductList.setLayoutManager(new LinearLayoutManager(getContext()));
        mPresenter.getHomeBannerIcon();
        rlSharePark.setOnClickListener(this);
        rlParkRecord.setOnClickListener(this);
        rlFastPay.setOnClickListener(this);
        platelist = UserInfoHelper.getInstance().getPlatelist();
        homeOrderAdapter = new HomeOrderAdapter(platelist);
        rvProductList.setAdapter(homeOrderAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_share_park:
                startActivity(new Intent(getActivity(), SharedParkingActivity.class));
                break;
            case R.id.rl_park_record:
                startActivity(new Intent(getActivity(), ParkedHistoryActivity.class));
                break;
            case R.id.rl_fast_pay:
                startActivity(new Intent(getActivity(), FastPayActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBusHelper.unRegister(this);
        super.onDestroy();
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void setBanner(HomeBannerIconEntity.DataBean banners) {
        if (banners == null || banners.getMainpageslide().size() == 0) return;
        if (banners.getMainpageslide().size() > 1) {
            mBanner.setCanLoop(true);
        } else {
            mBanner.setCanLoop(false);
        }
        mBanner.setPages(
                (CBViewHolderCreator<LocalImageHolderView>) new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, banners.getMainpageslide())
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.banner_point_normal, R.drawable.banner_point_select})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(2000);

        mBanner.setOnItemClickListener(position -> {
            // TODO: 2020/12/2
        });
    }

    @Override
    public void orderInfo(List<HomeOrderEntity.DataBean.OrderlistBean> orderlistBeans) {
        if (orderlistBeans != null && orderlistBeans.size() > 0) {
            homeOrderAdapter.addData(orderlistBeans);
        } else {
            View emptyView = View.inflate(getContext(), R.layout.home_eptview, null);
            View viewById = emptyView.findViewById(R.id.addcar);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, AddCarActivity.class));
                }
            });
            homeOrderAdapter.setEmptyView(emptyView);
        }
    }


    public class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
                }
            });

            imageView.setClipToOutline(true);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            Glide.with(AppUtils.getApp()).load(data).into(imageView);
        }
    }
}
