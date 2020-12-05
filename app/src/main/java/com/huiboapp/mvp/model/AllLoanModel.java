package com.huiboapp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.huiboapp.app.service.MyService;
import com.huiboapp.mvp.contract.AllLoanContract;
import com.huiboapp.mvp.model.entity.ParkListEntity;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class AllLoanModel extends BaseModel implements AllLoanContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AllLoanModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ParkListEntity> findAddrList(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).parkAddrList(params);
    }

}