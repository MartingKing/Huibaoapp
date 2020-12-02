package com.huiboapp.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.huiboapp.mvp.contract.MbaseContract;
import com.huiboapp.mvp.ui.activity.WebviewActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * BaseFragment扩展
 * <p>
 * Created by yaojian on 2019/1/23 18:46
 */
public abstract class MBaseFragment<P extends IPresenter> extends BaseFragment<P> implements MbaseContract.View, View.OnClickListener {

    @Override
    public void showMessage(@NonNull String message) {
        if (getActivity() != null)
            ArmsUtils.makeText(getActivity().getApplicationContext(), message);
    }

    @Override
    public void showLoading() {
        MBaseActivity activity = (MBaseActivity) getActivity();
        if (activity != null)
            activity.showLoading();
    }

    @Override
    public void hideLoading() {
        MBaseActivity activity = (MBaseActivity) getActivity();
        if (activity != null)
            activity.hideLoading();
    }

    protected void initViewClick(int id) {
    }

    @Override
    public void onClick(View v) {
        initViewClick(v.getId());
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

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

    public void toWebWithRequestParams(boolean hasTitle, String url, String title, int productId, String actionkey) {
        if (!url.startsWith("http") && !url.startsWith("https"))
            url = "http://" + url;
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putString("actionkey", actionkey);
        bundle.putInt("productId", productId);
        bundle.putBoolean("hasTitle", hasTitle);
        setIntent(WebviewActivity.class, bundle);
    }
}
