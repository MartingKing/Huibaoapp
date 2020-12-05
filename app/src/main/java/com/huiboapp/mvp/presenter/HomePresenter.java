package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.HomeContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.HomeOrderEntity;
import com.huiboapp.mvp.model.entity.MenberInfo;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

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
        Map<String, String> params = HBTUtls.getParams(HBTUtls.mainpageslide);
        params.put("token", UserInfoHelper.getInstance().getToken());
        mModel.getHomeBannerIcon(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<HomeBannerIconEntity>(mErrorHandler) {
                    @Override
                    public void onNext(HomeBannerIconEntity entity) {
                        mRootView.setBanner(entity.getData());
                    }
                });
    }

    public void getMemberInfo() {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.memberinfo);
        mModel.getMemberInfo(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<MenberInfo>(mErrorHandler) {
                    @Override
                    public void onNext(MenberInfo response) {
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void getOrderInfo() {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.queryorderlist);
        paramsObject.put("pageno",1);
        paramsObject.put("paystatus","unpaid");
        paramsObject.put("pagesize",1);
        mModel.getOrderInfo(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<HomeOrderEntity>(mErrorHandler) {
                    @Override
                    public void onNext(HomeOrderEntity response) {
                        mRootView.orderInfo(response.getData().getOrderlist());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }
}
