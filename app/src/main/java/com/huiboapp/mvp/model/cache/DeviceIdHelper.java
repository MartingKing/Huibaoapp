package com.huiboapp.mvp.model.cache;

import android.text.TextUtils;

import com.huiboapp.mvp.model.sp.DeviceIdSp;

/**
 * 设备缓存信息
 *
 * Created by yaojian on 2018/11/26 10:38
 */
public class DeviceIdHelper {

    private DeviceIdHelper() {
    }

    public static synchronized DeviceIdHelper getInstance() {
        return DeviceIdHolder.instance;
    }

    private static class DeviceIdHolder {
        private static final DeviceIdHelper instance = new DeviceIdHelper();
    }

    private String deviceId;

    public String getDeviceId() {
        if (TextUtils.isEmpty(deviceId))
            deviceId = DeviceIdSp.getParam(DeviceIdSp.deviceId, "");
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        DeviceIdSp.setParam(DeviceIdSp.deviceId, deviceId);
    }

}
