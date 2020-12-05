package com.huiboapp.mvp.model.sp;

import com.huiboapp.app.utils.SPUtils;
import com.huiboapp.mvp.model.entity.UserInfoEntity;

import java.util.List;


public class UserInfoSp {

    private static final String FILE_NAME = "uer_info";

    public static final String USER_NAME = "userName";
    public static final String USER_PHONE = "userPhone";
    public static final String USER_BALANCE = "balance";
    public static final String DEVICE_ID = "deviceId";
    public static final String SESSION1 = "session1";
    public static final String SESSION2 = "session2";
    public static final String COOKIE = "cookie";
    public static final String TOKEN = "token";
    public static final String CARLIST = "carlist";
    public static final String LOGIN_TIME = "login_time";


    public static SPUtils data() {
        return SPUtils.getInstance(FILE_NAME);
    }

    public static void clear() {
        data().clear();
    }

    public static void setParam(String key, String param) {
        data().put(key, param);
    }

    public static void setParam(String key, boolean param) {
        data().put(key, param);
    }

    public static void setParam(String key, int param) {
        data().put(key, param);
    }

    public static String getParam(String key) {
        return data().getString(key);
    }

    public static void setLongParam(String key, long time) {
        data().put(key, time);
    }

    public static long getLongParam(String key) {
        return data().getLong(key);
    }

    public static String getParam(String key, String defaultValue) {
        return data().getString(key, defaultValue);
    }

    public static boolean getBooleanParam(String key) {
        return data().getBoolean(key);
    }

    public static int getIntParam(String key) {
        return data().getInt(key, 0);
    }

    public static void setCarListParam(String key, List<UserInfoEntity.CarList> platelist) {
        data().setDataList(key, platelist);
    }

    public static List<UserInfoEntity.CarList> getCarListParam(String key) {
        return data().getDataList(key, UserInfoEntity.CarList.class);
    }
}
