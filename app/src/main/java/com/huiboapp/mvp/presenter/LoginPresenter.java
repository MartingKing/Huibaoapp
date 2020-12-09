package com.huiboapp.mvp.presenter;

import android.app.Application;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.LoginContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.CommonBooleanEntity;
import com.huiboapp.mvp.model.entity.UserInfoEntity;
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
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
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

    public void quickLogin(String mobile, String code) {
        Map<String, Object> params = HBTUtls.getParamsObject(HBTUtls.pwdlogin);
        params.put("loginname", mobile);
        params.put("password", code);
        mModel.quickLogin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfoEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfoEntity> response) {
                        if (response.isSuccess()) {
                            UserInfoHelper.getInstance().setLoginInfo(response.getData());
                            mRootView.loginSuccess();
                        } else {
                            mRootView.showMessage(TextUtils.isEmpty(response.getReason()) ? "登录失败" : response.getReason());
                        }
                    }
                });
    }


    public void checkImageCode(String imageCode, String phoneNum) {
        Map<String, String> params = new ArrayMap<>();
        params.put("signCode", imageCode);
        mModel.checkImageCode(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CommonBooleanEntity>(mErrorHandler) {
                    @Override
                    public void onNext(CommonBooleanEntity response) {
                        if (response.isData()) {
                            mRootView.getPhoneVerifyCode(phoneNum, imageCode);
                        } else {
                            mRootView.showMessage("图形验证码错误");
                        }
                    }
                });
    }
}
