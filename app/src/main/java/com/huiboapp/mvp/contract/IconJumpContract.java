package com.huiboapp.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.LatestProEntity;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


public interface IconJumpContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         *
         * @param products 产品列表数据
         * @param isloadermore 加载更多
         */
        void setProduct(List<ProductListEntity> products, boolean isloadermore);

        /**
         * 设置最新推荐产品
         * @param o 返回时候弹出的dialog上面的产品数据
         */
        void setLatestProduct(BaseResponse<LatestProEntity> o);

        void onError();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         *
         * @return  推荐产品
         */
        Observable<BaseResponse<LatestProEntity>> findLastestProduct();

        //首页加载数据埋点
        Observable<BaseResponse<NullEntity>> getHomeloadDataBurying(Map<String, Object> params);
    }
}
