package com.huiboapp.app.service;

import com.huiboapp.mvp.model.entity.BaseResponse;
import com.huiboapp.mvp.model.entity.ChargeEntity;
import com.huiboapp.mvp.model.entity.CommonBooleanEntity;
import com.huiboapp.mvp.model.entity.CommonEntity;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.huiboapp.mvp.model.entity.HomeOrderEntity;
import com.huiboapp.mvp.model.entity.ImageCodeEntity;
import com.huiboapp.mvp.model.entity.MenberInfo;
import com.huiboapp.mvp.model.entity.NullEntity;
import com.huiboapp.mvp.model.entity.OrderDetailInfo;
import com.huiboapp.mvp.model.entity.ParkListEntity;
import com.huiboapp.mvp.model.entity.PayEntity;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.huiboapp.mvp.model.entity.UserInfoEntity;
import com.huiboapp.mvp.model.entity.WelcomeEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
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
    @FormUrlEncoded
    Observable<BaseResponse<UserInfoEntity>> quickLogin(@FieldMap Map<String, Object> params);


    // 获取验证码
    @Headers({"Domain-Name: hbt"})
    @POST("security")
    @FormUrlEncoded
    Observable<BaseResponse<ImageCodeEntity>> getPhoneCode(@FieldMap Map<String, String> params);

    // 图片验证码
    @Headers({"Domain-Name: hbt"})
    @GET("security")
    Observable<BaseResponse<List<List<ImageCodeEntity>>>> getImageCode(@QueryMap Map<String, String> params);


    // 获取验证码
    @Headers({"Domain-Name: hbt"})
    @POST("security")
    @FormUrlEncoded
    Observable<BaseResponse<ImageCodeEntity>> resetPwd(@FieldMap Map<String, String> params);

    @Headers({"Domain-Name: hbt"})
    @POST("advert")
    @FormUrlEncoded
    Observable<BaseResponse<WelcomeEntity>> getSplash(@FieldMap Map<String, Object> params);

    //首页banner
    @Headers({"Domain-Name: hbt"})
    @POST("advert")
    @FormUrlEncoded
    Observable<HomeBannerIconEntity> getHomeBannerIcon(@FieldMap Map<String, String> params);

    @Headers({"Domain-Name: hbt"})
    @POST("car")
    @FormUrlEncoded
    Observable<CommonEntity> addCar(@FieldMap Map<String, Object> params);

    @Headers({"Domain-Name: hbt"})
    @POST("memberinfo")
    @FormUrlEncoded
    Observable<MenberInfo> memberinfo(@FieldMap Map<String, Object> params);

    @POST("order")
    Observable<HomeOrderEntity> orderinfo(@Body Map<String, Object> params);


    @POST("order")
    Observable<OrderDetailInfo> orderdetail(@Body Map<String, Object> params);

    @POST("order")
    Observable<PayEntity> getPayInfo(@Body Map<String, Object> params);

    @POST("parkinfo")
    Observable<ParkListEntity> parkAddrList(@Body Map<String, Object> params);

    @Headers({"Domain-Name: hbt"})
    @POST("account")
    Observable<ChargeEntity> charge(@Body Map<String, Object> params);

    @Headers({"Domain-Name: hbt"})
    @POST("memberinfo")
    Observable<CommonEntity> supplementary(@Body Map<String, Object> params);

    @Headers({"Domain-Name: hbt"})
    @POST("reserve")
    Observable<HomeOrderEntity> reservation(@Body Map<String, Object> params);

    // **************************  分界线   **************************
    @Headers({"Domain-Name: hbt"})
    @GET("/api/user/findUserInfo.do")
    Observable<BaseResponse<UserInfoEntity>> findUserInfo();

    // platform=1&deviceId=c25e7a8622d884a003f3223ab26150ad&_=1548640745704
    @Headers({"Domain-Name: hbt"})
    @GET("/api/statistic/active.do")
    Observable<BaseResponse<NullEntity>> active(@QueryMap Map<String, String> params);


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



    //数据埋点
    @Headers({"Domain-Name: hbt"})
    @POST("/api/statistic/countAction")
    Observable<BaseResponse<NullEntity>> dataBuryingPoint(@QueryMap Map<String, Object> params);

}
