package com.huiboapp.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.app.utils.UmengUtils;
import com.huiboapp.di.component.DaggerHomeComponent;
import com.huiboapp.di.module.HomeModule;
import com.huiboapp.mvp.contract.HomeContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity.BannerBean;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity.IconBean;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.huiboapp.mvp.presenter.HomePresenter;
import com.huiboapp.mvp.ui.activity.CommonJumpingEmptyProductActivity;
import com.huiboapp.mvp.ui.activity.IconJumpActivity;
import com.huiboapp.mvp.ui.activity.LoginActivity;
import com.huiboapp.mvp.ui.adapter.HomeIconListAdapter;
import com.huiboapp.mvp.ui.adapter.ProductListAdapter;
import com.huiboapp.mvp.ui.widget.refresh.EasyLoadMoreView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.EventBusHelper;
import com.jess.arms.utils.PjbTextUtils;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class HomeFragment extends MBaseFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.banner)
    ConvenientBanner mBanner;
    @BindView(R.id.rvProductList)
    RecyclerView rvProductList;


    ProductListAdapter mAdapter;
    HomeIconListAdapter mHomeIconListAdapter;
    private List<IconBean> mIconDatas;
    //偏移量
    private int pageSize = 10;
    //每页加载数据
    private int pageNo = 1;

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
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HomeFragment");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HomeFragment");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //产品列表adapter
        initProductListView();

        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.item_divider1)));
        rvProductList.addItemDecoration(divider);

        mPresenter.getHomeBannerIcon();

        mPresenter.findProductList(-1, pageNo, pageSize, false);

        mAdapter.setLoadMoreView(new EasyLoadMoreView());
        mAdapter.setOnLoadMoreListener(() -> {
            pageNo++;
            mPresenter.findProductList(-1, pageNo, pageSize, true);
        }, rvProductList);
    }

    private void initIconListView() {

    }


    private void initProductListView() {
        mAdapter = new ProductListAdapter();
        mAdapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) rvProductList.getParent());
        rvProductList.setLayoutManager(new LinearLayoutManager(mContext));
        rvProductList.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            UmengUtils.buriedListDatas(getContext(), "products_envent", mAdapter.getData().get(position).getProductName(), position);
            ProductListEntity product = (ProductListEntity) adapter.getData().get(position);
            String url = product.getProductUrl();
            String name = product.getProductName();
            int proId = mAdapter.getData().get(position).getId();

        });
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
    public void setBanner(List<BannerBean> banners) {
        if (banners == null || banners.size() == 0) return;
        if (banners.size() > 1) {
            mBanner.setCanLoop(true);
        } else {
            mBanner.setCanLoop(false);
        }
        mBanner.setPages(
                (CBViewHolderCreator<LocalImageHolderView>) () -> new LocalImageHolderView(), banners)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                //.setPageIndicator(new int[]{R.drawable.banner_point_normal, R.drawable.banner_point_select})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(2000);

        mBanner.setOnItemClickListener(position -> {
            int mPosition = position + 1;
            BannerBean banner = banners.get(position);

        });
    }

    @Override
    public void setIcon(List<IconBean> iconsDatas) {
        if (!iconsDatas.isEmpty()) {
            mIconDatas = iconsDatas;
            initIconListView();
            mHomeIconListAdapter.setNewData(mIconDatas);
        }
    }

    @Override
    public void setProduct(List<ProductListEntity> products, boolean isLoadmore) {
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

    @Override
    public void onError() {
    }

    private Bundle setBundleToEmptyJump(ProductListEntity product) {
        Bundle bundle = new Bundle();
        if (product != null) {
            bundle.putSerializable(MyConstant.HOME_EMPTY_JUMP, product);
        }
        return bundle;
    }

    public class LocalImageHolderView implements Holder<BannerBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, BannerBean data) {
            Picasso.get().load(PjbTextUtils.getDefaultText(data.getResourcePic(), "http://m3.yinpiaobao.cn/daichao/dev/201903/20190328/153426223.png"))
                    .into(imageView);
        }
    }
}
