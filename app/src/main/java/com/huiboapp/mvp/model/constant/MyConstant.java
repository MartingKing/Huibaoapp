package com.huiboapp.mvp.model.constant;

/**
 * 用来定义一些常量和传递参数的标识符
 */
public class MyConstant {

    public static boolean isShell = true;
    /**
     * 审核开关，审核期间为true，通过后关闭
     */
    public static boolean isTruggleOpen = false;
    public static String redirectType = "0";
    //从首页icon点击跳转的二级页面参数传递
    public static String ICON_TITLE = "icon_title";
    public static String ICON_SECOND_BANNER = "icon_second_banner";
    public static String ICON_SECOND_BG = "icon_second_bg";
    public static String ICON_ID = "icon_id";
    //每个产品列表页跳转传递参数(vico市场特别需求)
    public static String HOME_EMPTY_JUMP = "home_empty_jump";
    public static String CONSTANT_PHONE = "user_phone";
    public static String CONSTANT_PHONE_CODE = "phone_code";
    public static String CONSTANT_TAG = "verify_tag";
    public static String tagRegist = "registpwd";
    public static String tagResetpwd = "resetpwd";

    public static String VERIFY_CODE = "getvertificationcode";

    //跳转登录页面传递的数据
    public static String JUMP_URL = "jump_url";
    public static String PRO_NAME = "pro_name";
    public static String PRO_ID = "pro_id";
    public static String ACTION_KEY = "actionkey";
    //网络异常请求用户数据失败导致的token清理
    public static String NET_ERROR = "net_dely_to_clear_token";
    //
    public static int mmbTruggleValue = 0;
    public static String mmbTUrl = "";
}
