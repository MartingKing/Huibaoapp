package com.huiboapp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.huiboapp.app.service.MyService;
import com.huiboapp.mvp.contract.LoginContract;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.CommonBooleanEntity;
import com.huiboapp.mvp.model.entity.ImageCodeEntity;
import com.huiboapp.mvp.model.entity.UserInfoEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<UserInfoEntity>> quickLogin(Map<String, Object> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).quickLogin(params);
    }

    @Override
    public Observable<BaseResponse<List<List<ImageCodeEntity>>>> getImageCode() {
        return mRepositoryManager.obtainRetrofitService(MyService.class).getImageCode(null);
    }

    @Override
    public Observable<CommonBooleanEntity> checkImageCode(Map<String, String> params) {
        return mRepositoryManager.obtainRetrofitService(MyService.class).checkImageCode(params);
    }
}