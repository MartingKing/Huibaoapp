package com.huiboapp.app.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.huiboapp.R;
import com.huiboapp.mvp.contract.MbaseContract;
import com.huiboapp.mvp.ui.activity.WebviewActivity;
import com.huiboapp.mvp.ui.widget.dialog.LoadingDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;

import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * BaseActivity扩展
 * <p>
 * Created by yaojian on 2019/1/23 17:14
 */
public abstract class MBaseActivity<P extends IPresenter> extends BaseActivity<P> implements MbaseContract.View, View.OnClickListener {

    protected ImmersionBar immersionBar;

    protected int loadingCount;
    protected LoadingDialog loadingDialog;

    protected Bundle mBundle;
    protected boolean isBarDarkFont = true;
    protected boolean isBarBule = false;
    protected boolean hideKeyboard = true;

    /**
     * 初始化沉浸式状态栏
     */
    protected void initImmersionBar() {
        if (immersionBar == null) {
            immersionBar = ImmersionBar.with(this).statusBarDarkFont(isBarDarkFont);
            if (isBarBule)
                immersionBar.statusBarColor(R.color.app_status_bar);
        }
        immersionBar.init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        initImmersionBar();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void showLoading() {
        loadingCount++;
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog(this);
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingCount--;
        if (loadingCount == 0 && loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    protected void initViewClick(int id) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (immersionBar != null) {
            immersionBar.destroy();
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        loadingCount = 0;
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(getApplicationContext(), message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    public void toWeb(String url, String title, boolean hasTitle) {
        if (!url.startsWith("http") && !url.startsWith("https"))
            url = "http://" + url;

        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putBoolean("hasTitle", hasTitle);
        setIntent(WebviewActivity.class, bundle);
    }

    public void toWebWithRequestParams(boolean hasTitle, String url, String title, int productId, String requestkey) {
        if (!url.startsWith("http") && !url.startsWith("https"))
            url = "http://" + url;
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putString("requestkey", requestkey);
        bundle.putInt("productId", productId);
        bundle.putBoolean("hasTitle", hasTitle);
        setIntent(WebviewActivity.class, bundle);
    }

    /************************************************ 防止连续点击 开始**********************************************************/

    @Override
    public void onClick(View view) {
        // 防止连续点击
        if (com.huiboapp.app.utils.MUtils.isFastDoubleClick()) {
            Timber.d("onClick() 重复调用");
            return;
        }
        initViewClick(view.getId());
    }

    /************************************************ 防止连续点击 结束**********************************************************/

    /************************************************ 隐藏软键盘 开始***********************************************************/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (hideKeyboard && ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏软件盘
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, 0);
        }
    }

}
