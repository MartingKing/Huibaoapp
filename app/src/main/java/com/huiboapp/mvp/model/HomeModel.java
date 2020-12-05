package com.huiboapp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.huiboapp.app.service.MyService;
import com.huiboapp.mvp.contract.HomeContract;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.HomeOrderEntity;
import com.huiboapp.mvp.model.entity.MenberInfo;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override
    public Observable<HomeBannerIconEntity> getHomeBannerIcon(Map<String, String> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).getHomeBannerIcon(params);
    }

    @Override
    public Observable<HomeOrderEntity> getOrderInfo(Map<String, Object> params) {
        return  mRepositoryManager.obtainRetrofitService(MyService.class).orderinfo(params);
    }

    @Override
    public Observable<MenberInfo> getMemberInfo(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).memberinfo(params);
    }
}