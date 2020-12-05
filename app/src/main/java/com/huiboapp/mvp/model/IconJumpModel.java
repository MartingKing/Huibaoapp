package com.huiboapp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.huiboapp.app.service.MyService;
import com.huiboapp.mvp.contract.IconJumpContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.LatestProEntity;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class IconJumpModel extends BaseModel implements IconJumpContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public IconJumpModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<LatestProEntity>> findLastestProduct() {
        return mRepositoryManager.obtainRetrofitService(MyService.class).findLastestProduct();
    }

    @Override
    public Observable<BaseResponse<NullEntity>> getHomeloadDataBurying(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).dataBuryingPoint(params);
    }
}