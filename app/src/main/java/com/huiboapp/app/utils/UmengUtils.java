package com.huiboapp.app.utils;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;

import com.huiboapp.mvp.model.constant.MyConstant;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.Map;

import timber.log.Timber;

public class UmengUtils {

    public static void initUmeng(Context context) {
        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);
        CrashReport.initCrashReport(context, "b90c40448e", true);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_AUTO);
    }

    //友盟单个点击事件埋点
    public static void buriedSingleClickEvent(Context context, String eventId) {
        MobclickAgent.onEvent(context, eventId);
    }

    //友盟产品列表点击埋点
    public static void buriedListDatas(Context mContext, String eventId, String name, int position) {
        Map<String, String> homeProducts = new ArrayMap<>();
//        homeProducts.put("product_name", name);//自定义属性：产品名称
        homeProducts.put(eventId, name);//自定义属性：产品名称
        MobclickAgent.onEventValue(mContext, eventId, homeProducts, position);
    }


    public static Bundle setBundleData(String title, String pic, String bg, int id, String actionkey) {
        Bundle bundle = new Bundle();
        bundle.putString(MyConstant.ICON_SECOND_BANNER, pic);
        bundle.putString(MyConstant.ICON_TITLE, title);
        bundle.putString(MyConstant.ICON_SECOND_BG, bg);
        bundle.putInt(MyConstant.ICON_ID, id);
        bundle.putString(MyConstant.ACTION_KEY, actionkey);
        return bundle;
    }

    public static Bundle setLoginData(String url, String mame, int proId, String actionkey) {
        Bundle bundle = new Bundle();
        bundle.putString(MyConstant.JUMP_URL, url);
        bundle.putString(MyConstant.PRO_NAME, mame);
        bundle.putString(MyConstant.PRO_ID, String.valueOf(proId));
        bundle.putString(MyConstant.ACTION_KEY, actionkey);
        return bundle;
    }

    public static int binarySearch(long[] array, int size, long value) {
        int start = 0;
        int length = size - 1;

        while (start <= length) {
            int mid = start + length >>> 1;
            long midVal = array[mid];
            if (midVal < value) {
                start = mid + 1;
            } else {
                if (midVal <= value) {
                    return mid;
                }
                length = mid - 1;
            }
        }
        return ~start;
    }

    public static void testBinary() {
        long[] list = new long[]{1, 2, 3, 4, 5, 6, 7};
        Timber.e("testBinary:%d", binarySearch(list, list.length, 5));
    }
}
