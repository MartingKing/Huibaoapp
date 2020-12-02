package com.huiboapp.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.CommonBooleanEntity;
import com.huiboapp.mvp.model.entity.NewTruggleEntity;
import com.huiboapp.mvp.model.entity.VersionEntity;

import java.util.Map;

import io.reactivex.Observable;


public interface WelcomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void versionUpgrade(VersionEntity versionEntity);

        void setTruugle(boolean flag);

        void setNewTruggle(NewTruggleEntity newTruggle);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<VersionEntity>> getVersion(Map<String, Object> params);

    }
}
