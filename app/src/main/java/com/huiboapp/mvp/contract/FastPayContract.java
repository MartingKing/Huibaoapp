package com.huiboapp.mvp.contract;

import com.huiboapp.mvp.model.entity.HomeOrderEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.Map;

import io.reactivex.Observable;


public interface FastPayContract {
    interface View extends IView {
        void onderDetail(HomeOrderEntity commonEntity);
    }

    interface Model extends IModel {
        Observable<HomeOrderEntity> payOrder(Map<String, Object> params);
    }
}
