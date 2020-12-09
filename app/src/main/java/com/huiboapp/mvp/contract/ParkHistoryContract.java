package com.huiboapp.mvp.contract;

import com.huiboapp.mvp.model.entity.HomeOrderEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.Map;

import io.reactivex.Observable;


public interface ParkHistoryContract {
    interface View extends IView {

        void findOrderList(HomeOrderEntity orderEntity);
    }

    interface Model extends IModel {

        Observable<HomeOrderEntity> findOrderList(Map<String, Object> params);


    }
}
