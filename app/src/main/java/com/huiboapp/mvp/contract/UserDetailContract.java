package com.huiboapp.mvp.contract;

import com.huiboapp.mvp.model.entity.CommonEntity;
import com.huiboapp.mvp.model.entity.MenberInfo;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.Map;

import io.reactivex.Observable;


public interface UserDetailContract {
    interface View extends IView {
        void starPay(CommonEntity UserDetailEntity);
    }

    interface Model extends IModel {
        Observable<MenberInfo> getMemberInfo(Map<String, Object> params);
    }
}
