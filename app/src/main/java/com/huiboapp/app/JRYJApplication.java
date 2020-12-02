package com.huiboapp.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.commonlib.agentweb.utils.AppUtils;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.component.AppComponent;
import com.huiboapp.BuildConfig;
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
        initARouter();
        UmengUtils.initUmeng(this);
        Log.e("DHD", "onCreate: "+ com.huiboapp.app.utils.GetDeviceId.getDeviceId(this));
    }
    private void initARouter() {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
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
