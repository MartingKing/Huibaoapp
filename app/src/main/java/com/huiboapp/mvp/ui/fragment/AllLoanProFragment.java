package com.huiboapp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.app.utils.SPUtils;
import com.huiboapp.di.component.DaggerAllLoanComponent;
import com.huiboapp.di.module.AllLoanModule;
import com.huiboapp.mvp.contract.AllLoanContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.huiboapp.mvp.presenter.AllLoanPresenter;
import com.huiboapp.mvp.ui.adapter.RecProductListAdapter;
import com.huiboapp.mvp.ui.widget.refresh.EasyLoadMoreView;
import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 贷款大全
 */
public class AllLoanProFragment extends MBaseFragment<AllLoanPresenter> implements AllLoanContract.View {

    @BindView(R.id.rvRecProductList)
    RecyclerView rvRecProductList;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout mSmartrefreshlayout;
    @BindView(R.id.refreshHeader)
    ClassicsHeader refreshHeader;
    @BindView(R.id.tv_tab1)
    TextView tvTab1;
    @BindView(R.id.tv_tab2)
    TextView tvTab2;
    @BindView(R.id.tv_tab3)
    TextView tvTab3;
    @BindView(R.id.tv_tab4)
    TextView tvTab4;
    @BindView(R.id.tv_tab5)
    TextView tvTab5;
    @BindView(R.id.tv_tab6)
    TextView tvTab6;
    @BindView(R.id.tv_tab7)
    TextView tvTab7;
    @BindView(R.id.tv_tab8)
    TextView tvTab8;
    //偏移量
    private int pageSize = 30;
    //每页加载数据
    private int pageNo = 1;
    RecProductListAdapter mAdapter;
    private String mSlectIds = "";


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
        MobclickAgent.onPageStart("RecomendFragment");
        MobclickAgent.onResume(getActivity());
        SPUtils.getInstance().clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RecomendFragment");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.findRecommendList("-1", pageNo, pageSize, false);
        initTabList();
        initProductList();
        refreshData();
        mAdapter.setLoadMoreView(new EasyLoadMoreView());
        mAdapter.setOnLoadMoreListener(() -> {
            mAdapter.setEnableLoadMore(true);
            pageNo++;
            mPresenter.findRecommendList(TextUtils.isEmpty(mSlectIds) ? "-1" : mSlectIds, pageNo, pageSize, true);
        }, rvRecProductList);
    }
    //下拉刷新
    private void refreshData() {
        mSmartrefreshlayout.setOnRefreshListener(refreshlayout -> {
            mAdapter.setEnableLoadMore(false);
            pageNo = 1;
            mPresenter.findRecommendList(TextUtils.isEmpty(mSlectIds) ? "-1" : mSlectIds, pageNo, pageSize, false);
        });
    }

    Map<String, String> mTabMap = new ArrayMap<>(7);

    private void initTabList() {
        mTabMap.put("tab8", "8");
        tvTab8.setBackgroundResource(R.drawable.bg_border_red_1);
        tvTab8.setTextColor(ContextCompat.getColor(mContext, R.color.colorE00000));
        tvTab1.setOnClickListener(this);
        tvTab2.setOnClickListener(this);
        tvTab3.setOnClickListener(this);
        tvTab4.setOnClickListener(this);
        tvTab5.setOnClickListener(this);
        tvTab6.setOnClickListener(this);
        tvTab7.setOnClickListener(this);
        tvTab8.setOnClickListener(this);
    }

    private void initProductList() {
        mAdapter = new RecProductListAdapter();
        mAdapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) rvRecProductList.getParent());
        rvRecProductList.setLayoutManager(new LinearLayoutManager(mContext));
        rvRecProductList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProductListEntity product = mAdapter.getData().get(position);
            String url = product.getProductUrl();
            String name = product.getProductName();
            int proId = mAdapter.getData().get(position).getId();
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private boolean checkT1 = false, checkT2 = false, checkT3 = false, checkT4 = false, checkT5 = false, checkT6 = false, checkT7 = false, checkT8 = true;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_tab1:
                checkT1 = !checkT1;
                if (checkT1) {
                    tvTab1.setBackgroundResource(R.drawable.bg_border_red_1);
                    tvTab1.setTextColor(ContextCompat.getColor(mContext, R.color.colorE00000));
                    mTabMap.put("tab1", "1");
                } else {
                    tvTab1.setBackgroundResource(R.drawable.bg_border_gray_1);
                    tvTab1.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                    mTabMap.remove("tab1");
                }
                mTabMap.remove("tab8");
                tvTab8.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab8.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                break;
            case R.id.tv_tab2:
                checkT2 = !checkT2;
                if (checkT2) {
                    tvTab2.setBackgroundResource(R.drawable.bg_border_red_1);
                    tvTab2.setTextColor(ContextCompat.getColor(mContext, R.color.colorE00000));
                    mTabMap.put("tab2", "2");
                } else {
                    tvTab2.setBackgroundResource(R.drawable.bg_border_gray_1);
                    tvTab2.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                    mTabMap.remove("tab2");
                }
                mTabMap.remove("tab8");
                tvTab8.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab8.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                break;
            case R.id.tv_tab3:
                checkT3 = !checkT3;
                if (checkT3) {
                    tvTab3.setBackgroundResource(R.drawable.bg_border_red_1);
                    tvTab3.setTextColor(ContextCompat.getColor(mContext, R.color.colorE00000));
                    mTabMap.put("tab3", "3");
                } else {
                    tvTab3.setBackgroundResource(R.drawable.bg_border_gray_1);
                    tvTab3.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                    mTabMap.remove("tab3");
                }
                mTabMap.remove("tab8");
                tvTab8.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab8.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                break;
            case R.id.tv_tab4:
                checkT4 = !checkT4;
                if (checkT4) {
                    tvTab4.setBackgroundResource(R.drawable.bg_border_red_1);
                    tvTab4.setTextColor(ContextCompat.getColor(mContext, R.color.colorE00000));
                    mTabMap.put("tab4", "4");
                } else {
                    tvTab4.setBackgroundResource(R.drawable.bg_border_gray_1);
                    tvTab4.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                    mTabMap.remove("tab4");
                }
                mTabMap.remove("tab8");
                tvTab8.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab8.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                break;
            case R.id.tv_tab5:
                checkT5 = !checkT5;
                if (checkT5) {
                    tvTab5.setBackgroundResource(R.drawable.bg_border_red_1);
                    tvTab5.setTextColor(ContextCompat.getColor(mContext, R.color.colorE00000));
                    mTabMap.put("tab5", "5");
                } else {
                    tvTab5.setBackgroundResource(R.drawable.bg_border_gray_1);
                    tvTab5.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                    mTabMap.remove("tab5");
                }
                mTabMap.remove("tab8");
                tvTab8.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab8.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                break;
            case R.id.tv_tab6:
                checkT6 = !checkT6;
                if (checkT6) {
                    tvTab6.setBackgroundResource(R.drawable.bg_border_red_1);
                    tvTab6.setTextColor(ContextCompat.getColor(mContext, R.color.colorE00000));
                    mTabMap.put("tab6", "6");
                } else {
                    tvTab6.setBackgroundResource(R.drawable.bg_border_gray_1);
                    tvTab6.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                    mTabMap.remove("tab6");
                }
                mTabMap.remove("tab8");
                tvTab8.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab8.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                break;
            case R.id.tv_tab7:
                checkT7 = !checkT7;
                if (checkT7) {
                    tvTab7.setBackgroundResource(R.drawable.bg_border_red_1);
                    tvTab7.setTextColor(ContextCompat.getColor(mContext, R.color.colorE00000));
                    mTabMap.put("tab7", "7");
                } else {
                    tvTab7.setBackgroundResource(R.drawable.bg_border_gray_1);
                    tvTab7.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                    mTabMap.remove("tab7");
                }
                mTabMap.remove("tab8");
                tvTab8.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab8.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                break;
            case R.id.tv_tab8:
                checkT8 = !checkT8;
                mTabMap.clear();
                mTabMap.put("tab8", "8");
                tvTab8.setBackgroundResource(R.drawable.bg_border_red_1);
                tvTab8.setTextColor(ContextCompat.getColor(mContext, R.color.colorE00000));
                tvTab1.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab1.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                tvTab2.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab2.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                tvTab3.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab3.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                tvTab4.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab4.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                tvTab5.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab5.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                tvTab6.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab6.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                tvTab7.setBackgroundResource(R.drawable.bg_border_gray_1);
                tvTab7.setTextColor(ContextCompat.getColor(mContext, R.color.color666666));
                break;
        }
        List<String> label = new ArrayList<>(mTabMap.values());
        String mylab = label.toString();
        mSlectIds = mylab.substring(1, mylab.length() - 1);
        if (mSlectIds.contains("8")) {
            mSlectIds = "-1";
        }
        if (TextUtils.isEmpty(mSlectIds)) {
            mSlectIds = "-1";
            tvTab8.setBackgroundResource(R.drawable.bg_border_red_1);
            tvTab8.setTextColor(ContextCompat.getColor(mContext, R.color.colorE00000));
        }
        Log.e(TAG, "mSlectIds: mylab>>" + mylab);
        Log.e(TAG, "mSlectIds: " + mSlectIds);
        mPresenter.findRecommendList(TextUtils.isEmpty(mSlectIds) ? "-1" : mSlectIds, 1, pageSize, false);
    }

    @Override
    public void onError() {
        mSmartrefreshlayout.finishRefresh(false);
        refreshHeader.onFinish(mSmartrefreshlayout, false);
    }

    @Override
    public void setRecProduct(List<ProductListEntity> products, boolean isLoadmore) {
        mSmartrefreshlayout.finishRefresh();
        if (products == null || products.size() == 0) return;
        if (isLoadmore) {
            mAdapter.addData(products);
        } else {
            mAdapter.setNewData(products);
        }
        mAdapter.loadMoreComplete();
        if (products.size() < pageSize) {
            mAdapter.loadMoreEnd();
        }
    }

    private Bundle setBundleToEmptyJump(ProductListEntity product) {
        Bundle bundle = new Bundle();
        if (product != null) {
            bundle.putSerializable(MyConstant.HOME_EMPTY_JUMP, product);
        }
        return bundle;
    }

}
