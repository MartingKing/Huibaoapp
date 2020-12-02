package com.mmb.common;

import com.commonlib.agentweb.AgentWebSettingsImpl;
import com.commonlib.agentweb.IAgentWebSettings;
import com.commonlib.agentweb.utils.AppUtils;
import com.mmb.utils.CommonUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by lwc on 2019/7/12.
 */
public class MMbAgentSettingImpl extends AgentWebSettingsImpl {

    @Override
    public IAgentWebSettings toSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setUserAgentString(webSettings.getUserAgentString().concat("[%]DDJ_ANDROID_").concat(CommonUtils.getVersionName(AppUtils.getApp())).concat("[%]"));
        return super.toSetting(webView);
    }
}
