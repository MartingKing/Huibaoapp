package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.MyCarsContract;
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
public class MyCarsPresenter extends BasePresenter<MyCarsContract.Model, MyCarsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyCarsPresenter(MyCarsContract.Model model, MyCarsContract.View rootView) {
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

    public void deleteCar(String id, String plate, String color) {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.unbindcar);
        paramsObject.put("id",id);
        paramsObject.put("plate",plate);
        paramsObject.put("platecolor",color);
        mModel.deleteCar(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CommonEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CommonEntity response) {
                        mRootView.showMessage(response.getReason());

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }

    public void autoPay(String id, String plate, String color,boolean auto) {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.autopay);
        paramsObject.put("id",id);
        paramsObject.put("plate",plate);
        paramsObject.put("platecolor",color);
        paramsObject.put("autopay",auto);
        mModel.autoPay(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CommonEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CommonEntity response) {
                        mRootView.showMessage(response.getReason());

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }
}
