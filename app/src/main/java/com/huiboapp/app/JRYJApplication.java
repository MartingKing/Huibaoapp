package com.huiboapp.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.commonlib.agentweb.utils.AppUtils;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.component.AppComponent;
import com.huiboapp.app.utils.UmengUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

import timber.log.Timber;

public class JRYJApplication extends BaseApplication {
    public static Context applicationContext;

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 将MultiDex注入到项目中
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        getTestDeviceInfo(this);
        AppUtils.init(this);
        UmengUtils.initUmeng(this);
        Log.e("DHD", "onCreate: "+ com.huiboapp.app.utils.GetDeviceId.getDeviceId(this));
    }

    //{"device_id":"4f32214c","mac":"e4:46:da:ff:8e:87"}
    public static void getTestDeviceInfo(Context context) {
        try {
            if (context != null) {
                String deviceId = DeviceConfig.getDeviceIdForGeneral(context);
                String mac = DeviceConfig.getMac(context);
                Timber.e("deviceId%s", deviceId);
                Timber.e("mac%s", mac);
            }
        } catch (Exception ignored) {
        }

    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return super.getAppComponent();
    }

    public static String getChannelName(Context ctx) {
        return AnalyticsConfig.getChannel(ctx);
    }
}
