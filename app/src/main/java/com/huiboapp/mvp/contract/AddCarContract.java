package com.huiboapp.mvp.contract;

import com.huiboapp.mvp.model.entity.CommonEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.Map;

import io.reactivex.Observable;


public interface AddCarContract {
    interface View extends IView {
        void onSuccess();

        void onFailed(String reson);
    }

    interface Model extends IModel {
        Observable<CommonEntity> addCar(Map<String, Object> params);
    }
}
