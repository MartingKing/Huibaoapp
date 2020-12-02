package com.mmb.bridge;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Keep;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.mmb.entity.JsDataEntity;
import com.mmb.tencent_ocr.TecentIdentityReconitionActivity;

/**
 * 每加一个方法需要加上混淆
 */
@Keep
public class JsBridgeForTecentApi {
    private String TAG = "JsBridgeForMain";
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private JsMessageHandler mJsMessageHandler;

    public JsBridgeForTecentApi(JsMessageHandler jsMessageHandler) {
        mJsMessageHandler = jsMessageHandler;
    }

    @JavascriptInterface
    public void AndroidLog(String msg) {
        Log.i(TAG, "H5Log = " + msg);
    }

    @JavascriptInterface
    public void callAndroid(final String obj) {
        Log.e(TAG, "callAndroid_obj: " + obj);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                if (obj != null) {
                    JsDataEntity jsData = gson.fromJson(obj, JsDataEntity.class);
                    if (jsData.getEvent() != null) {
                        String operation = jsData.getEvent().getOperation();
                        TecentIdentityReconitionActivity.mMsgId = jsData.getMsgId();
                        if ("OCR".equals(operation)) {
                            String ocrType = jsData.getData().getOcrType();
                            TecentIdentityReconitionActivity.mOcrType = ocrType;
                            if ("0".equals(ocrType) || "1".equals(ocrType)) {
                                mJsMessageHandler.onOcrClicked(jsData.getData());
                            }
                            if ("2".equals(ocrType)) {
                                mJsMessageHandler.onPersionWithIdcardPicCalled();
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
                        if ("UnfinishAuth".equals(operation)) {
                            TecentIdentityReconitionActivity.isNeedIntercepeBackgress = true;
                        }
                        if ("FinishAuth".equals(operation)) {
                            TecentIdentityReconitionActivity.isNeedIntercepeBackgress = false;
                        }
                    }
                }
            }
        });

    }

    public interface JsMessageHandler {
        void onOcrClicked(JsDataEntity.DataBean mDatas);

        void onPersionWithIdcardPicCalled();

        void onAddressCalled();

        void onGpsCalled();

        void onVersionUpdate(String url);
    }
}
