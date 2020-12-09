package com.huiboapp.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.commonlib.agentweb.utils.AppUtils;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.app.utils.ToastUtils;
import com.huiboapp.di.component.DaggerUserComponent;
import com.huiboapp.di.module.UserModule;
import com.huiboapp.mvp.contract.UserContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.model.entity.UserFragEntity;
import com.huiboapp.mvp.presenter.UserPresenter;
import com.huiboapp.mvp.ui.activity.ChargeActivity;
import com.huiboapp.mvp.ui.activity.FeedbackActivity;
import com.huiboapp.mvp.ui.activity.HelpCenterActivity;
import com.huiboapp.mvp.ui.activity.LoginActivity;
import com.huiboapp.mvp.ui.activity.MyCarsActivity;
import com.huiboapp.mvp.ui.activity.SettingActivity;
import com.huiboapp.mvp.ui.activity.TuikuanActivity;
import com.huiboapp.mvp.ui.activity.UserDetailActivity;
import com.huiboapp.mvp.ui.adapter.UserFragAdapter;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的
 */
public class UserFragment extends MBaseFragment<UserPresenter> implements UserContract.View {

    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;
    @BindView(R.id.ivPortrait)
    ImageView ivPortrait;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_charge)
    TextView tvCharge;
    @BindView(R.id.lines)
    View lines;
    @BindView(R.id.tv_tuikuan)
    TextView tvTuikuan;
    @BindView(R.id.llayoutNotice)
    ConstraintLayout llayoutNotice;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.lines2)
    View lines2;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.rlUser)
    RelativeLayout rlUser;

    String[] iconName = {"车辆", "长租证", "消息", "发票", "帮助", "建议", "设置"};
    int[] icons = {R.mipmap.ic_car, R.mipmap.ic_czz, R.mipmap.ic_msg, R.mipmap.ic_fp, R.mipmap.ic_bz, R.mipmap.ic_jy, R.mipmap.ic_sz};

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
        ivBack.setVisibility(View.GONE);
        tvTitle.setText(R.string.app_name);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
        tvCharge.setOnClickListener(this);
        tvTuikuan.setOnClickListener(this);
        rlUser.setOnClickListener(this);
        UserFragAdapter adapter = new UserFragAdapter();
        recyclerview.setAdapter(adapter);
        List<UserFragEntity> datas = new ArrayList<>();
        for (int i = 0; i < iconName.length; i++) {
            UserFragEntity entity = new UserFragEntity();
            entity.setIcon(icons[i]);
            entity.setIconname(iconName[i]);
            datas.add(i, entity);
        }
        adapter.addData(datas);
        if (UserInfoHelper.getInstance().isLogin() && UserInfoHelper.getInstance().getUserInfo() != null) {
            tvUserName.setText(UserInfoHelper.getInstance().getUserInfo().getNickname());
            tvPhone.setText(UserInfoHelper.getInstance().getUserInfo().getMsisdn());
            tvAmount.setText(UserInfoHelper.getInstance().getUserInfo().getBalance());
        } else
            tvUserName.setText("请登录");

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getContext(), MyCarsActivity.class));
                        break;
                    case 1:
                        ToastUtils.showShort(AppUtils.getApp(), "长租车");
                        break;
                    case 2:
                        ToastUtils.showShort(AppUtils.getApp(), "消息");
                        break;
                    case 3:
                        ToastUtils.showShort(AppUtils.getApp(), "发票");
                        break;
                    case 4:
                        startActivity(new Intent(getContext(), HelpCenterActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getContext(), FeedbackActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getContext(), SettingActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    protected void initViewClick(int id) {
        super.initViewClick(id);
        switch (id) {
            case R.id.tvUserName:
                if (!UserInfoHelper.getInstance().isLogin()) {
                    setIntent(LoginActivity.class);
                }
                break;
            case R.id.tv_charge:
                startActivity(new Intent(getActivity(), ChargeActivity.class));
                break;
            case R.id.tv_tuikuan:
                startActivity(new Intent(getActivity(), TuikuanActivity.class));
                break;
            case R.id.rlUser:
                startActivity(new Intent(getActivity(), UserDetailActivity.class));
                break;
        }
    }

}
