package com.huiboapp.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.VerifyPhoneCodeContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.ImageCodeEntity;
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
public class VerifyPhoneCodePresenter extends BasePresenter<VerifyPhoneCodeContract.Model, VerifyPhoneCodeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public VerifyPhoneCodePresenter(VerifyPhoneCodeContract.Model model, VerifyPhoneCodeContract.View rootView) {
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

    public void getResetPwdPhoneCode(String phone) {
        Map<String, String> params = HBTUtls.getParams(HBTUtls.msg1);
        params.put("msisdn", phone);
        mModel.getPhoneCode(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ImageCodeEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ImageCodeEntity> response) {
                        Log.e("VerifyPhoneCode", "onNext: " + response.toString());
                        if (response.isSuccess()) {
                            mRootView.onSuccess();
                        } else {
                            mRootView.onFailue();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.e("VerifyPhoneCode", "onError: " + t.getMessage());
                    }
                });
    }

    public void getRgistPhoneCode(String phone) {
        Map<String, String> params = HBTUtls.getParams(HBTUtls.msg3);
        params.put("msisdn", phone);
        mModel.getPhoneCode(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ImageCodeEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ImageCodeEntity> response) {
                        Log.e("VerifyPhoneCode", "onNext: " + response.toString());
                        if (response.isSuccess()) {
                            mRootView.onSuccess();
                        } else {
                            mRootView.onFailue();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.e("VerifyPhoneCode", "onError: " + t.getMessage());
                    }
                });
    }
}
