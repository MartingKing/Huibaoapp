package com.huiboapp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.huiboapp.app.service.MyService;
import com.huiboapp.mvp.contract.WebviewContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.NullEntity;

import java.util.Map;

import io.reactivex.Observable;


@ActivityScope
public class WebviewModel extends BaseModel implements WebviewContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WebviewModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<NullEntity>> getProductDataBurying(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).dataBuryingPoint(params);
    }
}