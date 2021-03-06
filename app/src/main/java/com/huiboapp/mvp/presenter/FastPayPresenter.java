package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.FastPayContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.HomeOrderEntity;
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
public class FastPayPresenter extends BasePresenter<FastPayContract.Model, FastPayContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FastPayPresenter(FastPayContract.Model model, FastPayContract.View rootView) {
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

    public void fastPay(String plate, String platecolor) {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.queryorderlist);
        paramsObject.put("plate", plate);
        paramsObject.put("platecolor", platecolor);
        paramsObject.put("pageno", 1);
        paramsObject.put("paystatus", "unpaid");
        paramsObject.put("pagesize", 1);
        mModel.payOrder(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<HomeOrderEntity>(mErrorHandler) {
                    @Override
                    public void onNext(HomeOrderEntity response) {
                        if (response.getResult() == MyConstant.SUCCESS_CODE && response.getData().getOrderlist().size() > 0) {
                            mRootView.onderDetail(response);
                        } else {
                            mRootView.showMessage("没有待支付的账单");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

}
