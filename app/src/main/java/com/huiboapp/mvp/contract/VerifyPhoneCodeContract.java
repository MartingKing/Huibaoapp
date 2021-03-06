package com.huiboapp.mvp.contract;

import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.ImageCodeEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.Map;

import io.reactivex.Observable;


public interface VerifyPhoneCodeContract {
    interface View extends IView {
        void onSuccess();
        void onFailue();
    }

    interface Model extends IModel {
        Observable<BaseResponse<ImageCodeEntity>> getPhoneCode(Map<String, String> params);

    }
}
