package com.mmb.utils;

import android.app.Activity;

public final class AppManager {

    private static volatile AppManager sAppManager;

    public static AppManager getAppManager() {
        if (sAppManager == null) {
            synchronized (AppManager.class) {
                if (sAppManager == null) {
                    sAppManager = new AppManager();
                }
            }
        }
        return sAppManager;
    }

    /**
     * 退出应用程序
     * 多多借只有一个acitivity只需要直接finish掉，然后杀手进程就行
     * <p>
     */
    public void appExit(Activity activity) {
        try {
            activity.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
