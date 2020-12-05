package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.StopCarDetailContract;
import com.huiboapp.mvp.model.entity.OrderDetailInfo;
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
        paramsObject.put("orderid",orderid);
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
}
