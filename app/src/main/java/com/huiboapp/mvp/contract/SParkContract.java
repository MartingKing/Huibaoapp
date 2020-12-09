package com.huiboapp.mvp.contract;

import com.huiboapp.mvp.model.entity.ParkListEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.Map;

import io.reactivex.Observable;


public interface SParkContract {
    interface View extends IView {
        void parkinfo(ParkListEntity commonEntity);
    }

    interface Model extends IModel {
        Observable<ParkListEntity> parkinfo(Map<String, Object> params);
    }
}
