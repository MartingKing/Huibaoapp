package com.huiboapp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.di.component.DaggerUserComponent;
import com.jess.arms.di.component.AppComponent;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.app.utils.UmengUtils;
import com.huiboapp.di.module.UserModule;
import com.huiboapp.mvp.contract.UserContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.presenter.UserPresenter;
import com.huiboapp.mvp.ui.activity.AgreementWebActivity;
import com.huiboapp.mvp.ui.activity.FeedbackActivity;
import com.huiboapp.mvp.ui.activity.HelpCenterActivity;
import com.huiboapp.mvp.ui.activity.LoginActivity;
import com.huiboapp.mvp.ui.activity.SettingActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

/**
 * 我的
 */
public class UserFragment extends MBaseFragment<UserPresenter> implements UserContract.View {

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.llayoutHelpCenter)
    LinearLayout llayoutHelpCenter;
    @BindView(R.id.llayoutFeedback)
    LinearLayout llayoutFeedback;
    @BindView(R.id.llayoutPrivacyPolicy)
    LinearLayout llayoutPrivacyPolicy;
    @BindView(R.id.llayoutSetting)
    LinearLayout llayoutSetting;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .appComponent(appComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvUserName.setOnClickListener(this);

        llayoutHelpCenter.setOnClickListener(this);
        llayoutFeedback.setOnClickListener(this);
        llayoutPrivacyPolicy.setOnClickListener(this);
        llayoutSetting.setOnClickListener(this);
        if (UserInfoHelper.getInstance().isLogin()){
            tvUserName.setText(UserInfoHelper.getInstance().getUserName());
        }
        else
            tvUserName.setText("请登录");
    }

    @Override
    public void onResume() {
        super.onResume();
        assert mPresenter != null;
        mPresenter.findUserInfo();
        MobclickAgent.onPageStart("UserFragment");
        MobclickAgent.onResume(mContext);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("UserFragment");
        MobclickAgent.onPause(mContext);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser)
//            mPresenter.findUserInfo();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    protected void initViewClick(int id) {
        super.initViewClick(id);
        switch (id) {
            case R.id.tvUserName:
                UmengUtils.buriedSingleClickEvent(mContext, "login_event");
                if (!UserInfoHelper.getInstance().isLogin()) {
                    setIntent(LoginActivity.class);
                }
                break;
            case R.id.llayoutHelpCenter:
                setIntent(HelpCenterActivity.class);
                break;
            //联系客服
//            case R.id.llayoutCustomerService:
//                setIntent(ContactCustomerServiceActivity.class);
//                break;
            case R.id.llayoutFeedback:
                setIntent(FeedbackActivity.class);
                break;
            case R.id.llayoutPrivacyPolicy:
                setIntent(AgreementWebActivity.class);
                break;
            case R.id.llayoutSetting:
                if (UserInfoHelper.getInstance().isLogin()) {
                    setIntent(SettingActivity.class);
                } else {
//                    new DialogUtil(getContext(), true, R.style.dialog, "您还未登录，请您先完成登录！", (dialog, confirm) -> {
//                        if (confirm) {
//                            dialog.dismiss();
//                            setIntent(LoginActivity.class);
//                        } else {
//                            dialog.dismiss();
//                        }
//                    }).show();
                    setIntent(LoginActivity.class);
                }

                break;
        }
    }

    @Override
    public void setUsername() {
        if (UserInfoHelper.getInstance().isLogin())
            tvUserName.setText(UserInfoHelper.getInstance().getUserName());
        else
            tvUserName.setText("请登录");
    }
}
