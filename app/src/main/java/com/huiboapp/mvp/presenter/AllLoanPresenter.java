package com.huiboapp.mvp.presenter;

import android.app.Application;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.huiboapp.mvp.contract.AllLoanContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;

import java.util.List;
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

    public void findRecommendList(String label, int pageNo, int pageSize, boolean isLoadmore) {
        Map<String, String> params = new ArrayMap<>();
        params.put("resourceId", "-1");
        params.put("productLabels", label);
        params.put("pageNo", String.valueOf(pageNo));
        params.put("pageSize", String.valueOf(pageSize));
        mModel.findRecommendList(params, isLoadmore)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<ProductListEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<ProductListEntity>> response) {
                        if (response.isSuccess()) {
                            mRootView.setRecProduct(response.getData(), isLoadmore);
                        } else {
                            mRootView.onError();
                            mRootView.showMessage(TextUtils.isEmpty(response.getReason()) ? "未获取到产品列表" : response.getReason());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onError();
                    }
                });
    }

    //贷款大全产品列表点击埋点
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
}
