package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.mvp.contract.IconJumpContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.LatestProEntity;
import com.huiboapp.mvp.model.entity.NullEntity;
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
public class IconJumpPresenter extends BasePresenter<IconJumpContract.Model, IconJumpContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public IconJumpPresenter(IconJumpContract.Model model, IconJumpContract.View rootView) {
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


    public void getHomeloadDataBurying(Map<String, Object> params) {
        mModel.getHomeloadDataBurying(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<NullEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<NullEntity> response) {
                    }
                });
    }

    public void findLastestProduct() {
        mModel.findLastestProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<LatestProEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<LatestProEntity> response) {
                        mRootView.setLatestProduct(response);
                    }
                });
    }
}
