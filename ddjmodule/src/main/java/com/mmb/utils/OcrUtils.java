package com.mmb.utils;

import android.content.Context;
import android.util.Log;

import com.megvii.idcardquality.IDCardQualityLicenseManager;
import com.megvii.licensemanager.Manager;
import com.mmb.interfaces.OcrPermissionAuthListener;

public class OcrUtils {

    private Context mContext;
    private OcrPermissionAuthListener mPermissionAuthListener;

    public OcrUtils(Context context, OcrPermissionAuthListener permissionAuthListener) {
        mContext = context;
        mPermissionAuthListener = permissionAuthListener;
    }

    public void setOCRConfig(String mOcrType) {
        Log.e("JsBridgeForMain", "setOCRConfig: " + Integer.valueOf(mOcrType));
        Configuration.setCardType(mContext, Integer.valueOf(mOcrType) + 1);
        Configuration.setIsVertical(mContext, false);
        final IDCardQualityLicenseManager mIdCardLicenseManager = new IDCardQualityLicenseManager(mContext);
        long status = 0;
        try {
            status = mIdCardLicenseManager.checkCachedLicense();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (status > 0) {//大于0，已授权或者授权未过期
            mPermissionAuthListener.onPermissionAuthed();
        } else { //需要重新授权
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        getLicense(mIdCardLicenseManager);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void getLicense(IDCardQualityLicenseManager mIdCardLicenseManager) {
        Manager manager = new Manager(mContext);
        manager.registerLicenseManager(mIdCardLicenseManager);

        String uuid = Configuration.getUUID(mContext);
        String authMsg = mIdCardLicenseManager.getContext(uuid);
        manager.takeLicenseFromNetwork(authMsg);
        if (mIdCardLicenseManager.checkCachedLicense() > 0) {//大于0，已授权或者授权未过期
            mPermissionAuthListener.onPermissionAuthed();
        }
    }
}
