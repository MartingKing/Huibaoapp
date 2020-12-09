package com.huiboapp.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.ParkHistoryContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.HomeOrderEntity;
import com.jess.arms.di.scope.FragmentScope;
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


@FragmentScope
public class ParkHistoryPresenter extends BasePresenter<ParkHistoryContract.Model, ParkHistoryContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ParkHistoryPresenter(ParkHistoryContract.Model model, ParkHistoryContract.View rootView) {
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

    public void findOrderList(String status) {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.queryorderlist);
        if (!TextUtils.isEmpty(status)) {
            paramsObject.put("paystatus", status);
        }
        paramsObject.put("pageno", 1);
        paramsObject.put("pagesize", 1);
        mModel.findOrderList(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<HomeOrderEntity>(mErrorHandler) {
                    @Override
                    public void onNext(HomeOrderEntity response) {
                        if (response.getResult() == MyConstant.SUCCESS_CODE && response.getData().getOrderlist() != null) {
                            mRootView.findOrderList(response);
                        } else {
                            mRootView.showMessage(response.getReason());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

}
