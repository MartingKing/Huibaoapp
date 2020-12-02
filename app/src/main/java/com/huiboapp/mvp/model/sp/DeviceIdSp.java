package com.huiboapp.mvp.model.sp;

import com.huiboapp.app.utils.SPUtils;

/**
 * Created by yaojian on 2018/5/30 15:32
 */
public class DeviceIdSp {

    public static final String device_info = "PARAMS002";

    public static final String deviceId = "PARAM1010";
    public static final String hx_username = "PARAM1011";


    public static SPUtils data() {
        return SPUtils.getInstance(device_info);
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

    public static String getParam(String key, String defaultValue) {
        return data().getString(key, defaultValue);
    }

    public static boolean getBooleanParam(String key) {
        return data().getBoolean(key);
    }

    public static int getIntParam(String key) {
        return data().getInt(key, 0);
    }
}
