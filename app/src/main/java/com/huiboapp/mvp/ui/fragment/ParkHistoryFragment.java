package com.huiboapp.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.app.utils.ToastUtils;
import com.huiboapp.di.component.DaggerParkHistoryComponent;
import com.huiboapp.di.module.ParkHistoryModule;
import com.huiboapp.event.TabEvent;
import com.huiboapp.mvp.contract.ParkHistoryContract;
import com.huiboapp.mvp.model.entity.HomeOrderEntity;
import com.huiboapp.mvp.model.entity.PayInfo;
import com.huiboapp.mvp.presenter.ParkHistoryPresenter;
import com.huiboapp.mvp.ui.activity.PayOrderActivity;
import com.huiboapp.mvp.ui.adapter.ParkHistoryAdapter;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ParkHistoryFragment extends MBaseFragment<ParkHistoryPresenter> implements ParkHistoryContract.View {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.selectall)
    Button selectall;
    @BindView(R.id.payall)
    Button payall;
    @BindView(R.id.unpaicontainer)
    LinearLayout unpaicontainer;
    private ParkHistoryAdapter historyAdapter;
    String paystatus = "";
    List<PayInfo> payInfos = new ArrayList<>();
    List<Integer> checkItem = new ArrayList<>();

    public static ParkHistoryFragment newInstance() {
        return new ParkHistoryFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerParkHistoryComponent
                .builder()
                .appComponent(appComponent)
                .parkHistoryModule(new ParkHistoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragemnt_parkhis, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.findOrderList("");
        selectall.setOnClickListener(this);
        payall.setOnClickListener(this);



    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(TabEvent event) {
        paystatus = event.getParam();
        mPresenter.findOrderList(paystatus);
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    protected void initViewClick(int id) {
        switch (id) {
            case R.id.payall:
                if (historyAdapter != null) {
                    historyAdapter.setOnItemCheckListerner(new ParkHistoryAdapter.OnItemCheckListerner() {
                        @Override
                        public void onChecked(int pisition) {
                            checkItem.add(pisition);
                        }
                    });
                }
                if (checkItem.size() <= 0) {
                    ToastUtils.showShort(getContext(), "请选择要支付的订单!");
                    return;
                }
                if (checkItem.size() > 1) {
                    ToastUtils.showShort(getContext(), "请逐一支付订单!");
                    return;
                }
                HomeOrderEntity.DataBean.OrderlistBean orderInfo = mOrderlist.get(checkItem.get(0));
                PayInfo payInfo = new PayInfo();
                payInfo.setId(orderInfo.getId());
                payInfo.setPayamout(orderInfo.getTotalfee());
                payInfos.add(payInfo);
                String jsonData = new Gson().toJson(payInfos);
                Log.e(TAG, "getTotalfee: "+orderInfo.getTotalfee());
                Log.e(TAG, "jsonData: "+jsonData);
                Log.e(TAG, "getParksname: "+orderInfo.getParksname());
                startActivity(new Intent(getContext(), PayOrderActivity.class)
                        .putExtra("shouldPayFee", orderInfo.getTotalfee())
                        .putExtra("jsonData", jsonData)
                        .putExtra("parkAddr", orderInfo.getParksname()));
                break;
            case R.id.selectall:
                for (int i = 0; i < mOrderlist.size(); i++) {
                    mOrderlist.get(i).setChecked(false);
                    checkItem.clear();
                    historyAdapter.refreshNotifyItemChanged(i);
                }
                break;
        }
    }

    private List<HomeOrderEntity.DataBean.OrderlistBean> mOrderlist;

    @Override
    public void findOrderList(HomeOrderEntity orderEntity) {
        if (paystatus.equals("unpaid")) {
            unpaicontainer.setVisibility(View.VISIBLE);
        }
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new ParkHistoryAdapter(paystatus);
        recyclerview.setAdapter(historyAdapter);
        List<HomeOrderEntity.DataBean.OrderlistBean> orderlist = orderEntity.getData().getOrderlist();
        mOrderlist = orderlist;
        if (orderlist != null) {
            historyAdapter.setNewData(orderlist);
        }
    }
}
