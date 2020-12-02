package com.huiboapp.mvp.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.huiboapp.mvp.model.cache.DeviceIdHelper;
import com.huiboapp.mvp.ui.activity.WebviewActivity;

import timber.log.Timber;

/**
 * Created by yaojian on 2018/12/3 10:22
 */
public class AndroidInterface {

    private Handler deliver = new Handler(Looper.getMainLooper());
    private Context mContext;
    private View title;

    public AndroidInterface(Context mContext, View title) {
        this.mContext = mContext;
        this.title = title;
    }

    /**
     * 获取设备号
     */
    @JavascriptInterface
    public String getDeviceId() {
        String deviceId = DeviceIdHelper.getInstance().getDeviceId();
        Timber.d("mwebview__getDeviceId__%s", deviceId);
        return deviceId;
    }

//    /**
//     * 截图
//     */
//    @JavascriptInterface
//    public void screenShot() {
//        if (mContext instanceof WebActivity) {
//            WebActivity activity = (WebActivity) mContext;
//            activity.screenShot();
//        }
//    }

    /**
     * 使用手机里面的浏览器打开 url
     *
     * @param url 打开 url
     */
    @JavascriptInterface
    public void openBrowser(String url) {
        Timber.d("mwebview__openBrowser");
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(uri);
        mContext.startActivity(intent);
    }

    /**
     * 新开页面，并控制是否需要原生 TitleBar
     *
     * @param url         新页面 url
     * @param title       TitleBar 上的 title, 但是如果网页里面存在 title 标签优先使用 title 标签
     * @param hasTitleBar 是否需要原生 TitleBar
     */
    @JavascriptInterface
    public void newPageWithTitleBar(String url, String title, boolean hasTitleBar) {
        Timber.d("mwebview__newPageWithTitleBar");
        Intent intent = new Intent(mContext, WebviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putBoolean("hasTitle", hasTitleBar);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 新开页面，并显示原生 TitleBar
     *
     * @param url   新页面 url
     * @param title TitleBar 上的 title，但是如果网页里面存在 title 标签优先使用 title 标签
     */
    @JavascriptInterface
    public void newPageWithTitleBar(String url, String title) {
        newPageWithTitleBar(url, title, true);
    }

    /**
     * 新开页面，并显示原生 TitleBar
     *
     * @param url, 新页面 url，title 使用网页里面 title 标签
     */
    @JavascriptInterface
    public void newPageWithTitleBar(String url) {
        newPageWithTitleBar(url, null);
    }

    /**
     * 控制显示当前页面是否显示 TitleBar
     *
     * @param showTitleBar
     */
    @JavascriptInterface
    public void showTitleBar(boolean showTitleBar) {
        Timber.d("mwebview__%s", showTitleBar);
        deliver.post(() -> title.setVisibility(showTitleBar ? View.VISIBLE : View.GONE));
    }

    /**
     * 是否禁用系统返回键
     * 1 禁止
     */
    @JavascriptInterface
    public void shouldForbidSysBackPress(int forbid) {
        Timber.d("mwebview__shouldForbidSysBackPress");
        if (mContext instanceof WebviewActivity) {
            ((WebviewActivity)mContext).setCanBack(forbid != 1);
        }
    }

    /**
     * 头像获取
     *
     * @param callbackMethod
     */
    @JavascriptInterface
    public void takePortraitPicture(String callbackMethod) {
        Timber.d("mwebview__takePortraitPicture");
//        if (mContext instanceof WebActivity) {
//            ((WebActivity) mContext).takePortraitPicture(callbackMethod);
//        }
    }

    /**
     * 渠道推广首页，用于统计访客
     */
    @JavascriptInterface
    public void countGuest() {
        Timber.d("mwebview__countGuest");
//        if (mContext instanceof WebActivity) {
//            MobclickAgent.onEvent(mContext, "index");
//        }
    }

    /**
     * 注册人数
     */
    @JavascriptInterface
    public void countRegister() {
        Timber.d("mwebview__countRegister");
//        if (mContext instanceof WebActivity) {
//            MobclickAgent.onEvent(mContext, "regist");
//        }
    }

    /**
     * 点击首页产品列表
     */
    @JavascriptInterface
    public void clickIndexProd() {
        Timber.d("mwebview__clickIndexProd");
//        if (mContext instanceof WebActivity) {
//            MobclickAgent.onEvent(mContext, "clickIndexProd");
//        }
    }

    /**
     * 点击产品详情中_申请
     */
    @JavascriptInterface
    public void clickProdDetail() {
        Timber.d("mwebview__clickProdDetail");
//        if (mContext instanceof WebActivity) {
//            MobclickAgent.onEvent(mContext, "clickProdDetail");
//        }
    }

    /**
     * 获取个推设备id
     */
    @JavascriptInterface
    public String getGetuiDeviceId() {
        Timber.d("mwebview__getGetuiDeviceId");
//        if (mContext instanceof WebActivity) {
//            return PushManager.getInstance().getClientid(mContext);
//        }
        return "";
    }

}
