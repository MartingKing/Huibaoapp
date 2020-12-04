package com.huiboapp.app.service;

import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.CommonBooleanEntity;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.ImageCodeEntity;
import com.huiboapp.mvp.model.entity.LatestProEntity;
import com.huiboapp.mvp.model.entity.NewTruggleEntity;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.huiboapp.mvp.model.entity.UserInfoEntity;
import com.huiboapp.mvp.model.entity.WelcomeEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by yaojian on 2019/1/28 09:57
 */
public interface MyService {
    String APP_DOMAIN = "http://huibo.parkingquickly.com/smartparking/";// 服务器地址

    // 快速登录
    @Headers({"Domain-Name: hbt"})
    @POST("security")
    Observable<BaseResponse<UserInfoEntity>> quickLogin(@QueryMap Map<String, Object> params);


    // 获取验证码
    @Headers({"Domain-Name: hbt"})
    @POST("security")
    Observable<BaseResponse<ImageCodeEntity>> getPhoneCode(@QueryMap Map<String, String> params);

    // 图片验证码
    @Headers({"Domain-Name: hbt"})
    @GET("security")
    Observable<BaseResponse<List<List<ImageCodeEntity>>>> getImageCode(@QueryMap Map<String, String> params);


    // 获取验证码
    @Headers({"Domain-Name: hbt"})
    @POST("security")
    Observable<BaseResponse<ImageCodeEntity>> resetPwd(@QueryMap Map<String, String> params);

    @Headers({"Domain-Name: hbt"})
    @POST("advert")
    Observable<BaseResponse<WelcomeEntity>> getSplash(@QueryMap Map<String, Object> params);

    //首页banner和icon获取
    @Headers({"Domain-Name: hbt"})
    @POST("advert")
    Observable<HomeBannerIconEntity> getHomeBannerIcon(@QueryMap Map<String, String> params);






    // _=1548640745704
    @Headers({"Domain-Name: hbt"})
    @GET("/api/user/findUserInfo.do")
    Observable<BaseResponse<UserInfoEntity>> findUserInfo();

    // platform=1&deviceId=c25e7a8622d884a003f3223ab26150ad&_=1548640745704
    @Headers({"Domain-Name: hbt"})
    @GET("/api/statistic/active.do")
    Observable<BaseResponse<NullEntity>> active(@QueryMap Map<String, String> params);

    //获取产品列表
    @Headers({"Domain-Name: hbt"})
    @POST("/api/newAPi/productList")
    Observable<BaseResponse<List<ProductListEntity>>> getProductList(@QueryMap Map<String, Integer> params);

    //获取产品列表  贷款大全筛选
    @Headers({"Domain-Name: hbt"})
    @POST("/api/newAPi/productList")
    Observable<BaseResponse<List<ProductListEntity>>> getProductListLoan(@QueryMap Map<String, String> params);

    // 提交反馈
    // content=yyy&feedbackPic=&_=1548641566393
    @Headers({"Domain-Name: hbt"})
    @GET("/api/userFeedback/addFeedback.do")
    Observable<BaseResponse<NullEntity>> addFeedback(@QueryMap Map<String, String> params);


    // 退出
    // _=1548748412577
    @Headers({"Domain-Name: hbt"})
    @GET("/api/user/logout.do")
    Observable<BaseResponse<NullEntity>> logout();


    //校验图形验证码
    @Headers({"Domain-Name: hbt"})
    @GET("/api/user/graph_check")
    Observable<CommonBooleanEntity> checkImageCode(@QueryMap Map<String, String> params);


    // 最新产品推荐  api/newAPi/findLastestProduct
    @Headers({"Domain-Name: hbt"})
    @GET("/api/newAPi/findLastestProduct")
    Observable<BaseResponse<LatestProEntity>> findLastestProduct();

    //数据埋点
    @Headers({"Domain-Name: hbt"})
    @POST("/api/statistic/countAction")
    Observable<BaseResponse<NullEntity>> dataBuryingPoint(@QueryMap Map<String, Object> params);


    @Headers({"Domain-Name: hbt"})
    @GET("/api/other/config")
    Observable<CommonBooleanEntity> getTruggle();

    @Headers({"Domain-Name: hbt"})
    @GET("/api/other/new/switch")
    Observable<CommonBooleanEntity> getTruggleByChannel(@QueryMap Map<String, String> params);


    @Headers({"Domain-Name: hbt"})
    @GET("/api/other/v2.0/new/switch")
    Observable<BaseResponse<NewTruggleEntity>> getNewTruggle(@QueryMap Map<String, String> params);

}
