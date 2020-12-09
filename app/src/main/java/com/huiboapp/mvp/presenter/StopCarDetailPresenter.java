package com.huiboapp.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.StopCarDetailContract;
import com.huiboapp.mvp.model.entity.OrderDetailInfo;
import com.huiboapp.mvp.model.entity.PayEntity;
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
public class StopCarDetailPresenter extends BasePresenter<StopCarDetailContract.Model, StopCarDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StopCarDetailPresenter(StopCarDetailContract.Model model, StopCarDetailContract.View rootView) {
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

    public void getOrderDetail(String orderid) {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.queryorderdetail);
        paramsObject.put("orderid", orderid);
        mModel.orderDetail(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<OrderDetailInfo>(mErrorHandler) {
                    @Override
                    public void onNext(OrderDetailInfo response) {
                        mRootView.onderDetail(response);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }

    public void pay(String payInfos, String payamount, String model) {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.payorder);
        paramsObject.put("discount", null);
        paramsObject.put("orderlist", payInfos);
        paramsObject.put("payamount", payamount);
        paramsObject.put("apptype", model);
        Log.e(TAG, "payparkingorder: " + paramsObject);
        mModel.payOrder(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<PayEntity>(mErrorHandler) {
                    @Override
                    public void onNext(PayEntity response) {
                        if (response.getResult() == 300000) {
                            if (model.equals(HBTUtls.getPayModel(1))) {
                                mRootView.showMessage("支付成功");
                            }
                            mRootView.starPay(response);
                        } else {
                            if (response.getReason() != null)
                                mRootView.showMessage(response.getReason());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }

    public void fastPay(int payamount, String apptype) {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.querylatestorder);
        paramsObject.put("payamount", payamount);
        paramsObject.put("apptype", apptype);
        mModel.payOrder(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<PayEntity>(mErrorHandler) {
                    @Override
                    public void onNext(PayEntity response) {
                        mRootView.starPay(response);
                    }
                });
    }

}
