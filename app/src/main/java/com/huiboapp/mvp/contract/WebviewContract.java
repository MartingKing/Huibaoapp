package com.huiboapp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.NullEntity;

import java.util.Map;

import io.reactivex.Observable;


public interface WebviewContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        //为保证埋点接口请求到位，列表点击的第二个埋点接口放到webview中请求
        Observable<BaseResponse<NullEntity>> getProductDataBurying(Map<String, Object> params);
    }
}
