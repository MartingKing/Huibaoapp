package com.huiboapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huiboapp.BuildConfig;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.HProgressDialogUtils;
import com.huiboapp.di.component.DaggerWelcomeComponent;
import com.huiboapp.di.module.WelcomeModule;
import com.huiboapp.mvp.contract.WelcomeContract;
import com.huiboapp.mvp.model.entity.NewTruggleEntity;
import com.huiboapp.mvp.model.entity.VersionEntity;
import com.huiboapp.mvp.presenter.WelcomePresenter;
import com.huiboapp.mvp.ui.widget.dialog.VersionUpgradeDialog;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate._XUpdate;
import com.xuexiang.xupdate.service.OnFileDownloadListener;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 欢迎页
 */
public class WelcomeActivity extends MBaseActivity<WelcomePresenter> implements WelcomeContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWelcomeComponent
                .builder()
                .appComponent(appComponent)
                .welcomeModule(new WelcomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isBarDarkFont = false;
        //将window的背景图设置为空
        getWindow().setBackgroundDrawable(null);
        getWindow().setBackgroundDrawable(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                finish();
            }
        },500);
    }

    VersionUpgradeDialog dialog;

    @Override
    public void versionUpgrade(VersionEntity versionEntity) {
        String version = versionEntity.getVersion();
        String url = versionEntity.getUrl();
        String tips = versionEntity.getTips();
        version = version == null ? "" : version;
        int localVer = Integer.valueOf(BuildConfig.VERSION_NAME.replace(".", ""));
        int serVer = Integer.valueOf(version);

        if (dialog == null)
            dialog = new VersionUpgradeDialog(this, version, tips);
        dialog.setCancelable(false);
        dialog.setConfirmListener(v -> PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                download(url);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                showMessage("获取权限失败");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                showMessage("需要修改应用权限设置");
            }
        }, new RxPermissions(this), ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()));
//        dialog.setOnDismissListener(v -> {
//            dialog.dismiss();
//            mainPage();
//        });
        dialog.show();
    }

    @Override
    public void setTruugle(boolean flag) {
    }

    @Override
    public void setNewTruggle(NewTruggleEntity newTruggle) {

    }

    private void download(String url) {
        XUpdate.newBuild(WelcomeActivity.this)
                .build()
                .download(url, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(WelcomeActivity.this, "下载进度", false);
                    }

                    @Override
                    public void onProgress(float progress, long total) {
                        HProgressDialogUtils.setProgress(Math.round(progress * 100));
                    }

                    @Override
                    public boolean onCompleted(File file) {
                        HProgressDialogUtils.cancel();
                        // 安装apk
                        _XUpdate.startInstallApk(WelcomeActivity.this, file);
                        return false;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        HProgressDialogUtils.cancel();
                        showMessage("App下载失败,请切换网络或稍后重试。");
                    }
                });
    }

    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<WelcomeActivity> mActivity;
        private int s = 1;//2s后跳转

        private MyHandler(WelcomeActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WelcomeActivity activity = mActivity.get();
            if (activity == null) return;
            if (s-- == 0) {
                // RetrofitUrlManager.getInstance().putDomain("kdj", H5Urls.getH5Url());
                activity.setIntent(MainActivity.class);
                activity.finish();
            } else {
                sendEmptyMessageDelayed(1, 800);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
