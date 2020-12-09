package com.huiboapp.mvp.presenter;

import android.app.Application;

import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.UserDetailContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.MenberInfo;
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
public class UserDetailPresenter extends BasePresenter<UserDetailContract.Model, UserDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserDetailPresenter(UserDetailContract.Model model, UserDetailContract.View rootView) {
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

    public void getMemberInfo() {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.memberinfo);
        mModel.getMemberInfo(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<MenberInfo>(mErrorHandler) {
                    @Override
                    public void onNext(MenberInfo response) {
                        if (response.getResult() == MyConstant.SUCCESS_CODE && response.getData() != null)
                            mRootView.showMessage("保存成功");
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void smrz(String sex, String nickname, String headphoto, String realname, String idnumber, String identification_front, String identification_back) {
        Map<String, Object> paramsObject = HBTUtls.getParamsObject(HBTUtls.supplementary);
        paramsObject.put("sex", sex);
        paramsObject.put("nickname", nickname);
        paramsObject.put("photo", headphoto);
        paramsObject.put("realname", realname);
        paramsObject.put("idnumber", idnumber);
        paramsObject.put("identification_front", identification_front);
        paramsObject.put("identification_back", identification_back);
        mModel.getMemberInfo(paramsObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<MenberInfo>(mErrorHandler) {
                    @Override
                    public void onNext(MenberInfo response) {
                        if (response.getResult() == MyConstant.SUCCESS_CODE && response.getData() != null)
                            mRootView.showMessage("保存成功");
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }
}
