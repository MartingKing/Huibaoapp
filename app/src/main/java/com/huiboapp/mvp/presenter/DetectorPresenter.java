package com.huiboapp.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.huiboapp.mvp.contract.DetectorContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class DetectorPresenter extends BasePresenter<DetectorContract.Model, DetectorContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public DetectorPresenter(DetectorContract.Model model, DetectorContract.View rootView) {
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

    //一定借到钱数据埋点
    public void getHomeloadDataBurying(Map<String, Object> params) {
        mModel.getHomeloadDataBurying(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<NullEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<NullEntity> response) {

                    }
                });
    }

    public void findRecommendList(Map<String, String> params, boolean isLoadmore) {
        mModel.findRecommendList(params, isLoadmore)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<ProductListEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<ProductListEntity>> response) {
                        if (response.isSuccess()) {
                            mRootView.setRecProduct(response.getData(), isLoadmore);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onError();
                    }
                });
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
                            mRootView.setTab3Data(response.getData().getTabMatchBottomPage());
                            mRootView.setBannerBeforeMatch(response.getData().getTab3Page().get(0));
                            mRootView.setBannerAfterMatch(response.getData().getTabMatchHeadPage().get(0));
                        } else {
                            mRootView.showMessage(TextUtils.isEmpty(response.getReason()) ? "获取Banner失败" : response.getReason());
                        }
                    }
                });
    }
}
