package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.app.utils.SPUtils;
import com.huiboapp.mvp.contract.UserContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.UserInfoEntity;
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
import timber.log.Timber;


@FragmentScope
public class UserPresenter extends BasePresenter<UserContract.Model, UserContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserPresenter(UserContract.Model model, UserContract.View rootView) {
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

    public void findUserInfo() {
        mModel.findUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfoEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfoEntity> response) {
                        if (response.isSuccess() && response.getData() != null) {
                            UserInfoHelper.getInstance().setUserInfo(response.getData());
                            SPUtils.getInstance().put(MyConstant.NET_ERROR, "-1");
                        } else {
                            SPUtils.getInstance().put(MyConstant.NET_ERROR, "net_err");
                            UserInfoHelper.getInstance().clearUserInfo();
                        }
                        mRootView.setUsername();
                    }
                });
    }

    public void getHomeloadDataBurying(Map<String, Object> params) {
        mModel.getHomeloadDataBurying(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<NullEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<NullEntity> response) {
                        Timber.e("getHomeloadDataBurying%s", response.getReason());
                    }
                });
    }
}
