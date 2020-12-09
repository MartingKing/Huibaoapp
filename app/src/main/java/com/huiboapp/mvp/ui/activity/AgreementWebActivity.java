package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonlib.agentweb.AgentWeb;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerAgreementWebComponent;
import com.huiboapp.di.module.AgreementWebModule;
import com.huiboapp.mvp.contract.AgreementWebContract;
import com.huiboapp.mvp.presenter.AgreementWebPresenter;
import com.jess.arms.di.component.AppComponent;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;

/**
 * 隐私政策 todo 暂时无用的页面
 */
public class AgreementWebActivity extends MBaseActivity<AgreementWebPresenter> implements AgreementWebContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.llayoutWebview)
    LinearLayout llayoutWebview;
    private AgentWeb mAgentWeb;
    private String title = "隐私政策";
    private String url = "file:///android_asset/PrivacyPolicy.html";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAgreementWebComponent
                .builder()
                .appComponent(appComponent)
                .agreementWebModule(new AgreementWebModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_agreement_web;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (mBundle != null) {
            title = mBundle.getString("title");
            url = mBundle.getString("url");
        }
        initWebView();
    }

    private void initWebView() {
//        mAgentWeb = AgentWeb.with(this)
//                .setAgentWebParent(llayoutWebview, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f))
//                .useDefaultIndicator(-1, 1)
//                //.setWebViewClient(mWebViewClient)
//                .createAgentWeb()
//                .ready()
//                .go(url);
//
//        WebView webView = mAgentWeb.getWebCreator().getWebView();
//        webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() + " ANDROID_AGENT_NATIVE/1.0");
//
//        mAgentWeb.getJsInterfaceHolder().addJavaObject("AppJs",new AndroidInterface(this, rlayoutTitle));

        tvTitle.setText(title);
        ivBack.setOnClickListener(v -> {
            if (mAgentWeb == null) return;
            WebView wv = mAgentWeb.getWebCreator().getWebView();

            if (wv.canGoBack())
                wv.goBack();
            else
                finish();
        });
    }

}
