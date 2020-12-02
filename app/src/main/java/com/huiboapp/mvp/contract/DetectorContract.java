package com.huiboapp.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


public interface DetectorContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 产品列表数据
         * @param products datas
         * @param isLoadmore loadmore
         */
        void setRecProduct(List<ProductListEntity> products, boolean isLoadmore);

        /**
         * 立即匹配后跳转页面的数据
         * @param tabMatchBottomPage datas
         */
        void setTab3Data(List<HomeBannerIconEntity.TabMatchBottomPageBean> tabMatchBottomPage);

        /**
         * 立即匹配前顶部图片
         * @param url 立即匹配前顶部图片
         */
        void setBannerBeforeMatch(HomeBannerIconEntity.Tab3PageBean url);
        /**
         * @param url 立即匹配后顶部图片
         */
        void setBannerAfterMatch(HomeBannerIconEntity.TabMatchHeadPageBean url);

        void onError();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 数据埋点
         * @param params 参数
         * @return null
         */
        Observable<BaseResponse<NullEntity>> getHomeloadDataBurying(Map<String, Object> params);

        /**
         * 获取产品列表
         * @param params 请求参数
         * @param isLoadmore 加载更多
         * @return 产品列表数据
         */
        Observable<BaseResponse<List<ProductListEntity>>> findRecommendList(Map<String, String> params, boolean isLoadmore);

        /**
         * 获取banner和icon等资源数据
         * @return 资源数据
         */
        Observable<BaseResponse<HomeBannerIconEntity>> getHomeBannerIcon();
    }
}
