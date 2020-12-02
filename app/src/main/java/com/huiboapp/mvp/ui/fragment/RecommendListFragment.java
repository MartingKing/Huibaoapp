package com.huiboapp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.di.component.DaggerRecommendListComponent;
import com.huiboapp.di.module.RecommendListModule;
import com.huiboapp.mvp.contract.RecommendListContract;
import com.huiboapp.mvp.presenter.RecommendListPresenter;
import com.huiboapp.mvp.ui.adapter.RecommendListAdapter;

import butterknife.BindView;

/**
 * todo 暂时无用的页面
 */
public class RecommendListFragment extends MBaseFragment<RecommendListPresenter> implements RecommendListContract.View {

    @BindView(R.id.rvRecommendList)
    RecyclerView rvRecommendList;

    RecommendListAdapter mAdapter;

    int position = 0;

    public static RecommendListFragment newInstance(Bundle bundle) {
        RecommendListFragment fragment = new RecommendListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRecommendListComponent
                .builder()
                .appComponent(appComponent)
                .recommendListModule(new RecommendListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


    }

    /**
     * @Override
     *     public void setProduct(List<ProductEntity> products) {
     *         if (products == null || products.size() == 0) return;
     *
     *         if (fragments[0] == null) return;
     *
     *         fragments[0].setData(products);
     *         fragments[1].setData(products);
     *         fragments[2].setData(products);
     *     }
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
