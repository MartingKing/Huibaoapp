package com.huiboapp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.huiboapp.app.service.MyService;
import com.huiboapp.mvp.contract.StopCarDetailContract;
import com.huiboapp.mvp.model.entity.OrderDetailInfo;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class StopCarDetailModel extends BaseModel implements StopCarDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StopCarDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<OrderDetailInfo> orderDetail(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).orderdetail(params);
    }
}