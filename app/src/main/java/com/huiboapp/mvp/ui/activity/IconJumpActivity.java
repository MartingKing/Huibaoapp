package com.huiboapp.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.RecommendProDialogUtil;
import com.huiboapp.app.utils.UmengUtils;
import com.huiboapp.di.component.DaggerIconJumpComponent;
import com.huiboapp.di.module.IconJumpModule;
import com.huiboapp.mvp.contract.IconJumpContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.LatestProEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.huiboapp.mvp.presenter.IconJumpPresenter;
import com.huiboapp.mvp.ui.adapter.IconJumpAdapter;
import com.huiboapp.mvp.ui.widget.refresh.EasyLoadMoreView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.PjbTextUtils;
import com.huiboapp.mvp.ui.activity.CommonJumpingEmptyProductActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

/**
 * 首页icon跳转过来的页面
 */
public class IconJumpActivity extends MBaseActivity<IconJumpPresenter> implements IconJumpContract.View {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivBanner)
    ImageView mBanner;
    @BindView(R.id.srcoview)
    NestedScrollView mScrollView;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout mSmartrefreshlayout;
    @BindView(R.id.refreshHeader)
    ClassicsHeader refreshHeader;
    //偏移量
    private int pageSize = 10;
    //每页加载数据
    private int pageNo = 1;
    private LatestProEntity mDatas;
    private IconJumpAdapter mIconJumpAdapter;
    private String actionkey;
    private int mResourceId;
    private String color = "#ffffff";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerIconJumpComponent
                .builder()
                .appComponent(appComponent)
                .iconJumpModule(new IconJumpModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_iconjump;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initBundleData();
        initAdapter();
        initRefresh();
        ivBack.setOnClickListener(this);
    }

    private void initRefresh() {
        mPresenter.findProductList(mResourceId, pageNo, pageSize, false);
        mSmartrefreshlayout.setEnableLoadmore(false);
        mSmartrefreshlayout.setOnRefreshListener(refreshlayout -> {
            mIconJumpAdapter.setEnableLoadMore(true);
            pageNo = 1;
            mPresenter.findProductList(mResourceId, pageNo, pageSize, false);
        });
        mIconJumpAdapter.setLoadMoreView(new EasyLoadMoreView());
        mIconJumpAdapter.setOnLoadMoreListener(() -> {
            pageNo++;
            mPresenter.findProductList(mResourceId, pageNo, pageSize, true);
        }, mRecyclerView);
        mPresenter.findLastestProduct();
    }

    private void initAdapter() {
        mIconJumpAdapter = new IconJumpAdapter();
        mIconJumpAdapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) mRecyclerView.getParent());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mIconJumpAdapter);
        mIconJumpAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProductListEntity product = mIconJumpAdapter.getData().get(position);
            String url = product.getProductUrl();
            String name = product.getProductName();
            int proId = mIconJumpAdapter.getData().get(position).getId();

        });
    }

    private void initBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(MyConstant.ICON_TITLE)) {
                tvTitle.setText(bundle.getString(MyConstant.ICON_TITLE));
            }
            if (bundle.containsKey(MyConstant.ICON_SECOND_BANNER)) {
                Picasso.get().load(bundle.getString(MyConstant.ICON_SECOND_BANNER))
                        .fit()
                        .placeholder(R.mipmap.background_no_data)
                        .error(R.mipmap.background_no_data)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .config(Bitmap.Config.RGB_565)
                        .centerCrop()
                        .into(mBanner);
            }
            if (bundle.containsKey(MyConstant.ICON_SECOND_BG)) {
                color = PjbTextUtils.getDefaultText(bundle.getString(MyConstant.ICON_SECOND_BG), "#ffffff");

            }
            mResourceId = bundle.getInt(MyConstant.ICON_ID);
            if (bundle.containsKey(MyConstant.ACTION_KEY)) {
                actionkey = bundle.getString(MyConstant.ACTION_KEY);
            }
        }
    }

    @Override
    protected void initViewClick(int id) {
        if (id == R.id.ivBack) {
            showRecomendDialog();
        }
    }

    @Override
    public void onBackPressed() {
        showRecomendDialog();
    }

    @Override
    public void setProduct(List<ProductListEntity> products, boolean isLoadmore) {
        mSmartrefreshlayout.finishRefresh();
        if (products == null || products.size() == 0) return;
        if (!color.isEmpty() && color.contains("#")) {
            mScrollView.setBackgroundColor(Color.parseColor(color));
        } else {
            mRecyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorF4B04A));
        }

        if (isLoadmore) {
            mIconJumpAdapter.addData(products);
        } else {
            mIconJumpAdapter.setNewData(products);
        }
        mIconJumpAdapter.loadMoreComplete();
        if (products.size() < pageSize) {
            mIconJumpAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onError() {
        mSmartrefreshlayout.finishRefresh(false);
        refreshHeader.onFinish(mSmartrefreshlayout, false);

    }

    private Bundle setBundleToEmptyJump(ProductListEntity product) {
        Bundle bundle = new Bundle();
        if (product != null) {
            bundle.putSerializable(MyConstant.HOME_EMPTY_JUMP, product);
        }
        return bundle;
    }

    @Override
    public void setLatestProduct(BaseResponse<LatestProEntity> result) {
        mDatas = result.getData();
    }

    private void showRecomendDialog() {
        if (mDatas != null) {
            new RecommendProDialogUtil(this, true, R.style.dialog, mDatas, (dialog, confirm) -> {
                if (confirm) {
                    dialog.dismiss();
                    if (UserInfoHelper.getInstance().isLogin()) {
                        if (!MyConstant.isTruggleOpen)
                            toWeb(mDatas.getProductUrl(), mDatas.getProductName(), true);
                    } else {
                        setIntent(LoginActivity.class);
                    }
                } else {
                    IconJumpActivity.this.finish();
                }
            }).show();
        } else {
            finish();
        }
    }

}
