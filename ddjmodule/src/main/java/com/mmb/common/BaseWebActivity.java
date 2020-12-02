package com.mmb.common;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.commonlib.agentweb.AgentWeb;
import com.commonlib.agentweb.DefaultWebClient;
import com.mmb.contract.BaseContract;
import com.mmb.tencent_ocr.XqbChromeClient;
import com.mmb.utils.AndroidBug5497Workaround;
import com.mmb.utils.ColorUtil;
import com.mmb.utils.CommonUtils;
import com.mmb.utils.StatusBarUtil;
import com.mmb.view.TopTitleBar;


import java.util.ArrayList;

public abstract class BaseWebActivity extends AppCompatActivity implements BaseContract.BaseView{

    protected AgentWeb mAgentWeb;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        super.onCreate(savedInstanceState);
        setContentView(getRootLayoutViewResId());
        initBaseComponent();

        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarDarkTheme(this, true);


        onCreateFinal(savedInstanceState);
        initWebContent();
        onCreatAgentWebFinal(mAgentWeb);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initBaseComponent() {
        ARouter.getInstance().inject(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        CommonUtils.initX5();
        CommonUtils.initXupDate();
    }

    protected void initWebContent() {
        AndroidBug5497Workaround.assistActivity(this);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getWebParent(), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(new XqbChromeClient(BaseWebActivity.this))
//                .setWebViewClient(mWebViewClient)
//                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setAgentWebWebSettings(new MMbAgentSettingImpl())
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
//                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(getUrl());

        //去除QQ浏览器推广
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
                int size = outView.size();
                if (outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
    }


    protected abstract void onCreatAgentWebFinal(AgentWeb mAgentWeb);
    protected abstract ViewGroup getWebParent();
    protected abstract String getUrl();
    /**
     * 返回布局文件的resId
     *
     * @return resId
     */
    protected abstract int getRootLayoutViewResId();

    /**
     * 子activity的初始化逻辑
     * @param savedInstanceState 存留参数
     */
    protected abstract void onCreateFinal(@Nullable Bundle savedInstanceState);

    @Override
    public void resetSystemTitleBar() {
        StatusBarUtil.resetCommonUI(this);
    }

    @Override
    public void setStatusBarColor(int statusColor) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = this.getWindow();
//            //取消设置Window半透明的Flag
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //添加Flag把状态栏设为可绘制模式
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            //设置状态栏为透明
//            window.setStatusBarColor(statusColor);
//        }
        //如果是白色背景则设置黑色字体样式反之...
        if (StatusBarUtil.setStatusBarDarkTheme(this, !StatusBarUtil.isDarkColor(ColorUtil.int2Rgb(statusColor)))) {
//            StatusBarUtil.setStatusBarColor(this,statusColor);
        }else {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
//            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
    }


    @Override
    public TopTitleBar initIndicate() {
        if (getWebParent() instanceof LinearLayout && getWebParent().getChildCount() > 0){
            View childView = getWebParent().getChildAt(0);
            if (childView instanceof TopTitleBar){
                return (TopTitleBar)childView;
            }else {
                TopTitleBar bar = new TopTitleBar(this);
                bar.setImmersiveCompatViewHeight(StatusBarUtil.getStatusBarHeight(this));
//                bar.setPadding(bar.getPaddingLeft(),StatusBarUtil.getStatusBarHeight(this),bar.getPaddingRight(),bar.getPaddingBottom());
                getWebParent().addView(bar,0);
                bar.setLeftIvListener(new TopTitleBar.TopBarListener() {
                    @Override
                    public void onRightIvListener(View view) {
                        mAgentWeb.back();
                    }
                });
                return bar;
            }
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
