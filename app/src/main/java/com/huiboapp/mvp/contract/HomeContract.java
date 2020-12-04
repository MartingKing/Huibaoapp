package com.huiboapp.mvp.contract;

import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


public interface HomeContract {
    interface View extends IView {
        /**
         * @param banners 轮播图数据
         */
        void setBanner(HomeBannerIconEntity.DataBean banners);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<HomeBannerIconEntity> getHomeBannerIcon(Map<String, String> params);

        Observable<BaseResponse<List<ProductListEntity>>> findProductList(Map<String, Integer> params, boolean isLoadmore);

        //首页加载数据埋点
        Observable<BaseResponse<NullEntity>> getHomeloadDataBurying(Map<String, Object> params);

    }
}
