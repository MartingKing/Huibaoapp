package com.huiboapp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.huiboapp.mvp.contract.DetectorContract;
import com.huiboapp.app.service.MyService;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class DetectorModel extends BaseModel implements DetectorContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DetectorModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<NullEntity>> getHomeloadDataBurying(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).dataBuryingPoint(params);
    }

    @Override
    public Observable<BaseResponse<List<ProductListEntity>>> findRecommendList(Map<String, String> params, boolean is) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).getProductListLoan(params);
    }

    @Override
    public Observable<BaseResponse<HomeBannerIconEntity>> getHomeBannerIcon() {
        return mRepositoryManager.obtainRetrofitService(MyService.class).getHomeBannerIcon();
    }
}