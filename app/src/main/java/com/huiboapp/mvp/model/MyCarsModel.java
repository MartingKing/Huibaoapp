package com.huiboapp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.huiboapp.app.service.MyService;
import com.huiboapp.mvp.contract.MyCarsContract;
import com.huiboapp.mvp.model.entity.CommonEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class MyCarsModel extends BaseModel implements MyCarsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyCarsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<CommonEntity> deleteCar(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).addCar(params);
    }

    @Override
    public Observable<CommonEntity> autoPay(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).addCar(params);
    }
}