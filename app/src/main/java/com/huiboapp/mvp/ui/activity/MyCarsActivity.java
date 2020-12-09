package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerMyCarsComponent;
import com.huiboapp.di.module.MyCarsModule;
import com.huiboapp.event.CommonEvent;
import com.huiboapp.mvp.contract.MyCarsContract;
import com.huiboapp.mvp.model.entity.UserInfoEntity;
import com.huiboapp.mvp.presenter.MyCarsPresenter;
import com.huiboapp.mvp.ui.adapter.MyCarsAdapter;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class MyCarsActivity extends MBaseActivity<MyCarsPresenter> implements MyCarsContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private MyCarsAdapter myCarsAdapter;
    private List<UserInfoEntity.CarList> platelist;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyCarsComponent
                .builder()
                .appComponent(appComponent)
                .myCarsModule(new MyCarsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_mycars;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(CommonEvent event) {
        platelist = event.getPlatelist();
        Log.e(TAG, "onMainEvent2: " + platelist);

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText("我的车辆");
        myCarsAdapter = new MyCarsAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(myCarsAdapter);
        myCarsAdapter.addData(platelist);
        myCarsAdapter.setDeleteBindedCarInterface(new MyCarsAdapter.DeleteBindedCarInterface() {
            @Override
            public void delete(String id, String plate, String color) {
                mPresenter.deleteCar(id, plate, color);
            }

            @Override
            public void onAutoPay(String id, String plate, String color, boolean auto) {
                mPresenter.autoPay(id, plate, color, auto);
            }
        });
    }


    @Override
    protected void initViewClick(int id) {
        super.initViewClick(id);
        switch (id) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnNext:

                break;
        }
    }

}
