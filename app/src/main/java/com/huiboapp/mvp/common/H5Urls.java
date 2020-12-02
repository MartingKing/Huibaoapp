package com.huiboapp.mvp.common;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.huiboapp.app.utils.Utils;

/**
 * Created by yaojian on 2018/12/5 18:05
 */
public class H5Urls {
    private static String IP_DOMAIN = "http://119.147.212.149:18088";

    public static String getH5UrlFromChannel() {
        return getH5Url(getMetaData());
    }

    public static String getH5Url(String url) {

        return url;

//        if (channel.equals("HW")) {
//            return "http://hwdaichao.ichainsoft.com";
//        }
//        if (channel.equals("XM")) {
//            return "http://xmdaichao.ichainsoft.com";
//        }
//        if (channel.equals("VIVO")) {
//            return "http://vivodaichao.ichainsoft.com";
//        }
//        if (channel.equals("OPPO")) {
//            return "http://oppodaichao.ichainsoft.com";
//        }
//        if (channel.equals("YYB")) {
//            return "http://yybdaichao.ichainsoft.com";
//        }
//        if (channel.equals("S360")) {
//            return "http://360daichao.ichainsoft.com";
//        }
//        if (channel.equals("BD91")) {
//            return "http://bd91daichao.ichainsoft.com";
//        }
//        if (channel.equals("ALPP")) {
//            return "http://alppdaichao.ichainsoft.com";
//        }
//        if (channel.equals("AZ")) {
//            return "http://azydaichao.ichainsoft.com";
//        }
//        if (channel.equals("MMY")) {
//            return "http://mmydaichao.ichainsoft.com";
//        }
//        if (channel.equals("MZ")) {
//            return "http://mzdaichao.ichainsoft.com";
//        }
//        if (channel.equals("SX")) {
//            return "http://sxdaichao.ichainsoft.com";
//        }
//        if (channel.equals("CZ")) {
//            return "http://czdaichao.ichainsoft.com";
//        }
//        if (channel.equals("LX")) {
//            return "http://lxdaichao.ichainsoft.com";
//        }
//        if (channel.equals("JT")) {
//            return "http://justoken.ichainsoft.com";
//        }
//        if (channel.equals("Google")) {
//            return "http://goog.ichainsoft.com";
//        }
//        return "http://yybdaichao.ichainsoft.com";
    }

    public static String getMetaData() {
        String channel = "";
        try {
            ApplicationInfo appInfo = Utils.getApp().getPackageManager().getApplicationInfo(Utils.getApp().getPackageName(), PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }

}
