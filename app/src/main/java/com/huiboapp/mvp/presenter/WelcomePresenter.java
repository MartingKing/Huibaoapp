package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.WelcomeContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.WelcomeEntity;
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
public class WelcomePresenter extends BasePresenter<WelcomeContract.Model, WelcomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WelcomePresenter(WelcomeContract.Model model, WelcomeContract.View rootView) {
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

    public void getSplash() {
        Map<String, Object> params = HBTUtls.getParamsObject(HBTUtls.splash);
        // 切换baseurl
        //RetrofitUrlManager.getInstance().putDomain("kdj", H5Urls.getH5Url("home_list"));
        mModel.getSplash(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<WelcomeEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<WelcomeEntity> response) {
                        if (response.isSuccess() && response.getData() != null){
                            mRootView.getSplashUrl(response.getData().getSlash());
                        }else {
                            mRootView.onFailed();
                        }

                    }
                });
    }


}
