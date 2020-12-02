package com.huiboapp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.huiboapp.app.service.MyService;
import com.huiboapp.mvp.contract.WelcomeContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.CommonBooleanEntity;
import com.huiboapp.mvp.model.entity.NewTruggleEntity;
import com.huiboapp.mvp.model.entity.VersionEntity;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class WelcomeModel extends BaseModel implements WelcomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WelcomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<VersionEntity>> getVersion(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).getVersion(params);
    }

}
