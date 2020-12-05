package com.huiboapp.mvp.ui.adapter;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.commonlib.agentweb.utils.AppUtils;
import com.huiboapp.R;
import com.huiboapp.app.utils.DateUtil;
import com.huiboapp.mvp.model.entity.HomeOrderEntity.DataBean.OrderlistBean;
import com.huiboapp.mvp.model.entity.UserInfoEntity;
import com.huiboapp.mvp.ui.activity.AddCarActivity;
import com.huiboapp.mvp.ui.activity.MyCarsActivity;
import com.huiboapp.mvp.ui.activity.StopCarDetailActivity;

import java.io.Serializable;
import java.util.List;


public class HomeOrderAdapter extends BaseQuickAdapter<OrderlistBean, BaseViewHolder> {

    private List<UserInfoEntity.CarList> carlist;

    public HomeOrderAdapter(List<UserInfoEntity.CarList> platelist) {
        super(R.layout.item_home_order, null);
        this.carlist = platelist;
        Log.e(TAG, "HomeOrderAdapter: " + carlist);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderlistBean item) {
        helper.setText(R.id.parking_address, item.getParksname());
        helper.setText(R.id.parking_time, "进入时间: " + DateUtil.getDataString(item.getParkbegin()));
        helper.setText(R.id.out_time, "驶出时间: " + DateUtil.getDataString(item.getParkend()));
        helper.setText(R.id.total_time, DateUtil.getDataString(item.getParkend() - item.getParkbegin()));
        TextView brand_one = helper.getView(R.id.brand_one);
        TextView brand_two = helper.getView(R.id.brand_two);
        TextView brand_three = helper.getView(R.id.brand_three);
        ConstraintLayout uncheck_order = helper.getView(R.id.uncheck_order);
        if (carlist != null) {
            uncheck_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, StopCarDetailActivity.class).putExtra("orderid",item.getId()));
                }
            });
            int size = carlist.size();
            if (size == 0) {
                brand_one.setVisibility(View.VISIBLE);
                brand_one.setText("");
                brand_one.setBackgroundResource(R.mipmap.ic_addcar);
                brand_two.setVisibility(View.GONE);
                brand_three.setVisibility(View.GONE);
                brand_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, AddCarActivity.class));
                    }
                });
            }
            if (size == 1) {
                brand_one.setText(carlist.get(0).getPlate());
                getColor(carlist.get(0).getPlatecolor(), brand_one);
                brand_one.setVisibility(View.VISIBLE);
                brand_two.setText("");
                brand_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, MyCarsActivity.class).putExtra("platelist", (Serializable) carlist));
                    }
                });
                brand_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, AddCarActivity.class));
                    }
                });
                brand_two.setBackgroundResource(R.mipmap.ic_addcar);
                brand_two.setVisibility(View.VISIBLE);
                brand_three.setVisibility(View.GONE);
            }
            if (size >= 2) {
                brand_one.setText(carlist.get(0).getPlate());
                getColor(carlist.get(0).getPlatecolor(), brand_one);
                brand_two.setText(carlist.get(1).getPlate());
                getColor(carlist.get(1).getPlatecolor(), brand_two);
                brand_one.setVisibility(View.VISIBLE);
                brand_two.setVisibility(View.VISIBLE);
                brand_three.setVisibility(View.VISIBLE);

                brand_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, MyCarsActivity.class));
                    }
                });
                brand_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, MyCarsActivity.class));
                    }
                });
                brand_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, AddCarActivity.class));
                    }
                });
            }
        }
    }

    private void getColor(String color, TextView v) {
        if (color.equals("blue")) {
            v.setBackgroundResource(R.drawable.bg_gradient_blue);
            v.setTextColor(AppUtils.getApp().getResources().getColor(R.color.white));
        }
        if (color.equals("green")) {
            v.setBackgroundResource(R.drawable.bg_gradient_green);
            v.setTextColor(AppUtils.getApp().getResources().getColor(R.color.color333));
        }
        if (color.equals("yellow")) {
            v.setBackgroundResource(R.drawable.shape_corner1_gradient_yello);
            v.setTextColor(AppUtils.getApp().getResources().getColor(R.color.white));
        }
    }

}
