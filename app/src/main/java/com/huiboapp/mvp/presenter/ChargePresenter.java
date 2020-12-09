package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.ChargeContract;
import com.huiboapp.mvp.model.entity.ChargeEntity;
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
public class ChargePresenter extends BasePresenter<ChargeContract.Model, ChargeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ChargePresenter(ChargeContract.Model model, ChargeContract.View rootView) {
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

    public void chargeMoney(int payamount,String apptype) {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.accountrecharge);
        paramsObject.put("payamount",payamount);
        paramsObject.put("apptype",apptype);
        mModel.chargeMoney(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<ChargeEntity>(mErrorHandler) {
                    @Override
                    public void onNext(ChargeEntity response) {
                        mRootView.starPay(response);
                    }
                });
    }


}
