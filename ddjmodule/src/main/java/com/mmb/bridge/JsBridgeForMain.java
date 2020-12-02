package com.mmb.bridge;

import android.os.Looper;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.mmb.DdjMainActivity;
import com.mmb.entity.JsDataEntity;

/**
 * Created by lwc on 2019/6/26.
 */
@Keep
public class JsBridgeForMain {
    private String TAG = "JsBridgeForMain";
    private android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper());
    private JsMessageHandler mJsMessageHandler;

    public JsBridgeForMain(JsMessageHandler jsMessageHandler) {
        mJsMessageHandler = jsMessageHandler;
    }

    @JavascriptInterface
    public void AndroidLog(String msg) {
        Log.i(TAG, "H5Log = " + msg);
    }

    @JavascriptInterface
    public void callAndroid(final String obj) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                if (obj != null) {
                    JsDataEntity jsData = gson.fromJson(obj, JsDataEntity.class);
                    Log.e(TAG, "callAndroid_obj: " + jsData);
                    if (jsData.getEvent() != null) {
                        String operation = jsData.getEvent().getOperation();
                        DdjMainActivity.mMsgId = jsData.getMsgId();
                        if ("OCR".equals(operation)) {
                            String ocrType = jsData.getData().getOcrType();
                            DdjMainActivity.mOcrType = ocrType;
                            if ("0".equals(ocrType) || "1".equals(ocrType)) {
                                mJsMessageHandler.onOcrClicked();
                            }
                            if ("2".equals(ocrType)) {
                                mJsMessageHandler.onPersionWithIdcardPicCalled();
                            }
                        }
                        if ("Face".equals(operation)) {
                            String bizToken = jsData.getData().getBizTokenStr();
                            if (!TextUtils.isEmpty(bizToken)) {
                                mJsMessageHandler.onMegliveClicked(bizToken);
                            }
                        }
                        if ("PhoneContact".equals(operation)) {
                            mJsMessageHandler.onAddressCalled();
                        }
                        if ("GPS".equals(operation)) {
                            mJsMessageHandler.onGpsCalled();
                        }
                        if ("GPSStatus".equals(operation)) {
                            mJsMessageHandler.onGpsCalled();
                        }
                        if ("TurnToSettingPhone".equals(operation)) {
                            String apkUrl = jsData.getData().getUrl();
                            mJsMessageHandler.onVersionUpdate(apkUrl);
                        }
                        if ("nativeback".equals(operation)) {
                            mJsMessageHandler.isBackPressed();
                        }
                    }
                }
            }
        });

    }

    public interface JsMessageHandler {
        void onOcrClicked();

        void onMegliveClicked(String bizToken);

        void onPersionWithIdcardPicCalled();

        void onAddressCalled();

        void onGpsCalled();

        void onVersionUpdate(String url);

        void isBackPressed();
    }
}
