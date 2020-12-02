package com.huiboapp.mvp.presenter;

import android.app.Application;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.huiboapp.app.utils.RegexUtils;
import com.huiboapp.mvp.contract.FeedbackContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.NullEntity;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class FeedbackPresenter extends BasePresenter<FeedbackContract.Model, FeedbackContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FeedbackPresenter(FeedbackContract.Model model, FeedbackContract.View rootView) {
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

    public void addFeedback(String content, String phone){
        if (TextUtils.isEmpty(content)){
            mRootView.showMessage("请输入内容");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            mRootView.showMessage("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            mRootView.showMessage("请输入正确的手机号");
            return;
        }

        Map<String, String> params = new ArrayMap<>();
        params.put("content", content);
        params.put("phone", phone);
        params.put("feedbackPic", "");

        mModel.addFeedback(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<NullEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<NullEntity> response) {
                        if (response.isSuccess()) {
                            mRootView.clearData();
                            mRootView.showMessage("提交成功");
                        } else {
                            mRootView.showMessage(TextUtils.isEmpty(response.getResult()) ? "提交失败" : response.getResult());
                        }
                    }
                });
    }
}
