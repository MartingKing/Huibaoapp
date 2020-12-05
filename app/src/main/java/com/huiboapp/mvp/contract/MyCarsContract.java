package com.huiboapp.mvp.contract;

import com.huiboapp.mvp.model.entity.CommonEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.Map;

import io.reactivex.Observable;


public interface MyCarsContract {
    interface View extends IView {

    }

    interface Model extends IModel {
        Observable<CommonEntity> deleteCar(Map<String, Object> params);
        Observable<CommonEntity> autoPay(Map<String, Object> params);
    }
}
