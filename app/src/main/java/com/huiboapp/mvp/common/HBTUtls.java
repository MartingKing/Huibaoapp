package com.huiboapp.mvp.common;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.commonlib.agentweb.utils.AppUtils;
import com.huiboapp.app.utils.RegexUtils;
import com.jess.arms.utils.ArmsUtils;

import java.util.Map;

public class HBTUtls {

    public static boolean checkInput(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            ArmsUtils.makeText(AppUtils.getApp(), "请输入手机号！");
            return false;
        }
        if (!RegexUtils.isMobileSimple(mobile)) {
            ArmsUtils.makeText(AppUtils.getApp(), "请输入正确的手机号！");
            return false;
        }
        return true;
    }

    public static final String msg1 = "getregistedvertificationcode";
    public static final String msg2 = "updatememberpassword";

    public static Map<String, String> getParams(String msg) {
        Map<String, String> params = new ArrayMap<>();
        params.put("msg", msg);
        params.put("channelid", "1");
        params.put("msgid", "1000");
        params.put("sign", "12345");
        return params;
    }

    public static Map<String, Object> getParamsObject(String msg) {
        Map<String, Object> params = new ArrayMap<>();
        params.put("msg", msg);
        params.put("channelid", "1");
        params.put("msgid", "1000");
        params.put("sign", "12345");
        return params;
    }
}
