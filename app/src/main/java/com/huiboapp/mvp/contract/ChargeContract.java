package com.huiboapp.mvp.contract;

import com.huiboapp.mvp.model.entity.ChargeEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.Map;

import io.reactivex.Observable;


public interface ChargeContract {
    interface View extends IView {
        void starPay(ChargeEntity chargeEntity);
    }

    interface Model extends IModel {
        Observable<ChargeEntity> chargeMoney(Map<String, Object> params);

    }
}
