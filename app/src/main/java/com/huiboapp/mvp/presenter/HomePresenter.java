package com.huiboapp.mvp.presenter;

import android.app.Application;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.huiboapp.mvp.contract.HomeContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getHomeBannerIcon() {
        mModel.getHomeBannerIcon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HomeBannerIconEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HomeBannerIconEntity> response) {
                        if (response.isSuccess()) {
                            mRootView.setBanner(response.getData().getBanner());
                            mRootView.setIcon(response.getData().getIcon());
                        } else {
                            mRootView.showMessage(TextUtils.isEmpty(response.getResult()) ? "获取Banner失败" : response.getResult());
                        }
                    }
                });
    }

    public void findProductList(int id, int pageNo, int pageSize, boolean isLoadmore) {
        Map<String, Integer> params = new ArrayMap<>();
        params.put("resourceId", id);
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        mModel.findProductList(params, isLoadmore)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<ProductListEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<ProductListEntity>> response) {
                        if (response.isSuccess()) {
                            mRootView.setProduct(response.getData(), isLoadmore);
                        } else {
                            mRootView.onError();
                            mRootView.showMessage(TextUtils.isEmpty(response.getResult()) ? "未获取到产品列表" : response.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onError();
                    }
                });
    }
}
