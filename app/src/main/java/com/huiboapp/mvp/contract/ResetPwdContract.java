package com.huiboapp.mvp.contract;

import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.ImageCodeEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.Map;

import io.reactivex.Observable;


public interface ResetPwdContract {
    interface View extends IView {
        void onResetSuccess();
        void onResetFailue();
        void onRegistSuccess();
        void onRegistFailue();
    }

    interface Model extends IModel {
        Observable<BaseResponse<ImageCodeEntity>> resetPwd(Map<String, String> params);
    }
}
