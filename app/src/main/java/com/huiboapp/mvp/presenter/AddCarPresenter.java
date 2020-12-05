package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.mvp.contract.AddCarContract;
import com.huiboapp.mvp.model.entity.CommonEntity;
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
public class AddCarPresenter extends BasePresenter<AddCarContract.Model, AddCarContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AddCarPresenter(AddCarContract.Model model, AddCarContract.View rootView) {
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

    public void addYourCar(Map<String, Object> params) {
        mModel.addCar(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CommonEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CommonEntity response) {
                        if (response.getResult() == 300000) {
                            mRootView.onSuccess();
                        } else {
                            mRootView.onFailed(response.getReason());
                        }
                    }
                });
    }
}
