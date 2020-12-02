package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonlib.agentweb.AgentWeb;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.UmengUtils;
import com.huiboapp.di.component.DaggerWebviewComponent;
import com.huiboapp.di.module.WebviewModule;
import com.huiboapp.mvp.common.AndroidInterface;
import com.huiboapp.mvp.contract.WebviewContract;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.presenter.WebviewPresenter;
import com.jess.arms.di.component.AppComponent;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;


/**
 * webview
 */
public class WebviewActivity extends MBaseActivity<WebviewPresenter> implements WebviewContract.View {

    @BindView(R.id.llayoutWebview)
    LinearLayout llayoutWebview;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private AgentWeb mAgentWeb;
    private String title;
    private String url;
    private String actionkey;
    private int productId;
    private boolean hasTitle = true;

    private boolean canBack = true;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebviewComponent
                .builder()
                .appComponent(appComponent)
                .webviewModule(new WebviewModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_webview;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            url = bundle.getString("url");
            actionkey = bundle.getString("actionkey");
            productId = bundle.getInt("productId");
            hasTitle = bundle.getBoolean("hasTitle", true);
            if (bundle.containsKey("canBack"))
                canBack = bundle.getBoolean("canBack");
        }
        initWebView();
    }

    private void initWebView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(llayoutWebview, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f))
                .useDefaultIndicator(-1, 1)
                //.setWebViewClient(mWebViewClient)
                .createAgentWeb()
                .ready()
                .go(url);

        WebView webView = mAgentWeb.getWebCreator().getWebView();
        webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() + " ANDROID_AGENT_NATIVE/1.0");

        mAgentWeb.getJsInterfaceHolder().addJavaObject("AppJs", new AndroidInterface(this, rlayoutTitle));

        rlayoutTitle.setVisibility(hasTitle ? View.VISIBLE : View.GONE);
        tvTitle.setText(TextUtils.isEmpty(title) ? getText(R.string.app_name) : title);
        ivBack.setOnClickListener(v -> {
            if (mAgentWeb == null) return;
            WebView wv = mAgentWeb.getWebCreator().getWebView();

            if (wv.canGoBack())
                wv.goBack();
            else
                finish();
        });
    }

    @Override
    public void onBackPressed() {
        if (!canBack) {
            super.onBackPressed();
            return;
        }

        WebView webView = mAgentWeb.getWebCreator().getWebView();

        if (webView.canGoBack()) {
            webView.goBack();
        } else
            super.onBackPressed();
    }

    public void setCanBack(boolean canBack) {
        this.canBack = canBack;
    }

}
