package com.huiboapp.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity.BannerBean;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity.IconBean;
import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.UserInfoEntity;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * @param banners 轮播图数据
         */
        void setBanner(List<BannerBean> banners);

        /**
         * @param icons 轮播图下面icon数据
         */
        void setIcon(List<IconBean> icons);

        /**
         * @param products   热门产品数据
         * @param isLoadmore 加载更多
         */
        void setProduct(List<ProductListEntity> products, boolean isLoadmore);

        void onError();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<UserInfoEntity>> findUserInfo();

        /**
         * @return banner和icon数据，以及起点击事件二级页面初始化数据
         */
        Observable<BaseResponse<HomeBannerIconEntity>> getHomeBannerIcon();

        /**
         * @param params     请求参数
         * @param isLoadmore 加载更多
         * @return 首页产品列表
         */
        Observable<BaseResponse<List<ProductListEntity>>> findProductList(Map<String, Integer> params, boolean isLoadmore);

        //首页加载数据埋点
        Observable<BaseResponse<NullEntity>> getHomeloadDataBurying(Map<String, Object> params);

    }
}
