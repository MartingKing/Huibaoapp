package com.mmb.tencent_ocr;

import android.app.Activity;
import android.net.Uri;

import com.commonlib.agentweb.WebChromeClient;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;

import java.lang.ref.WeakReference;

/**
 * Created by lwc on 2019/6/13.
 */
public class XqbChromeClient extends WebChromeClient {
    /**
     * Activity
     */
    private WeakReference<Activity> mActivityWeakReference = null;

    public XqbChromeClient(Activity activity) {
        mActivityWeakReference = new WeakReference<Activity>(activity);
    }

    /**
     * android端接收H5端发来的请求
     * For Android >= 3.0
     */
    @Override
    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        if (WBH5FaceVerifySDK.getInstance().recordVideoForApiBelow21(uploadMsg, acceptType, mActivityWeakReference.get()))
            return;
        // TODO: 第三方有调用系统相机处理其他业务的话，将相关逻辑代码放在下面

    }

    /**
     * android端接收H5端发来的请求
     * For Android >= 4.1
     */
    @Override
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        if (WBH5FaceVerifySDK.getInstance().recordVideoForApiBelow21(uploadMsg, acceptType, mActivityWeakReference.get()))
            return;
        // TODO: 第三方有调用系统相机处理其他业务的话，将相关逻辑代码放在下面

    }

    /**
     * android端接收H5端发来的请求
     * For Lollipop 5.0+ Devices
     */
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (WBH5FaceVerifySDK.getInstance().recordVideoForApi21(webView, filePathCallback, mActivityWeakReference.get(), fileChooserParams)) {
            return true;
        }
        // TODO: 第三方有调用系统相机处理其他业务的话，将相关逻辑代码放在下面
        return true;
    }

}
