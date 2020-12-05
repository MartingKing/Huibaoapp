package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.AllLoanContract;
import com.huiboapp.mvp.model.entity.ParkListEntity;
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
public class AllLoanPresenter extends BasePresenter<AllLoanContract.Model, AllLoanContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AllLoanPresenter(AllLoanContract.Model model, AllLoanContract.View rootView) {
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

    public void findParkList() {
        Map<String, Object> params = HBTUtls.getParamsObject(HBTUtls.queryparkslist);
        mModel.findAddrList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<ParkListEntity>(mErrorHandler) {
                    @Override
                    public void onNext(ParkListEntity response) {
                        mRootView.setRecProduct(response,true);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onError();
                    }
                });
    }

}
