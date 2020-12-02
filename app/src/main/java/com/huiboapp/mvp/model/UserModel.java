package com.huiboapp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.huiboapp.mvp.contract.UserContract;
import com.huiboapp.app.service.MyService;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.UserInfoEntity;
import com.huiboapp.mvp.model.entity.NullEntity;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class UserModel extends BaseModel implements UserContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<UserInfoEntity>> findUserInfo() {
        return mRepositoryManager.obtainRetrofitService(MyService.class).findUserInfo();
    }

    @Override
    public Observable<BaseResponse<NullEntity>> getHomeloadDataBurying(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).dataBuryingPoint(params);
    }
}