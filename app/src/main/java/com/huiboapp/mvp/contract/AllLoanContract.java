package com.huiboapp.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


public interface AllLoanContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 设置列表数据
         * @param products 产品数据
         * @param isLoadmore 是否加载更多
         */
        void setRecProduct(List<ProductListEntity> products, boolean isLoadmore);

        void onError();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取贷款大全产品列表
         * @param params 请求参数
         * @param isLoadmore 是否加载更多
         * @return data
         */
        Observable<BaseResponse<List<ProductListEntity>>> findRecommendList(Map<String, String> params, boolean isLoadmore);

        //数据埋点
        Observable<BaseResponse<NullEntity>> getHomeloadDataBurying(Map<String, Object> params);
    }
}
