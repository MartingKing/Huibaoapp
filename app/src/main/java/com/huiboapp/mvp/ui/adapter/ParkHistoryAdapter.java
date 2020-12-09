package com.huiboapp.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.commonlib.agentweb.utils.AppUtils;
import com.huiboapp.R;
import com.huiboapp.app.utils.DateUtil;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.HomeOrderEntity;


public class ParkHistoryAdapter extends BaseQuickAdapter<HomeOrderEntity.DataBean.OrderlistBean, BaseViewHolder> {

    private String mtag;

    public ParkHistoryAdapter(String tag) {
        super(R.layout.item_park_his);
        this.mtag = tag;
    }
    public interface OnItemCheckListerner{
        void onChecked(int pisition);
    }
    private OnItemCheckListerner onItemCheckListerner;

    public void setOnItemCheckListerner(OnItemCheckListerner onItemCheckListerner) {
        this.onItemCheckListerner = onItemCheckListerner;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeOrderEntity.DataBean.OrderlistBean item) {
        helper.setText(R.id.tvtotaltime, DateUtil.getDataString(item.getParkend() - item.getParkbegin()).substring(12));
        helper.setText(R.id.tvcarnum, item.getPlate());
        helper.setText(R.id.tv_addres_desc, item.getParksname());
        helper.setText(R.id.tvstarttime, DateUtil.getDataString(item.getParkbegin()));
        helper.setText(R.id.tvendtime, DateUtil.getDataString(item.getParkend()));
        RadioButton radioButton = helper.getView(R.id.radio);
        LinearLayout container = helper.getView(R.id.container);
        ImageView tvstatus = helper.getView(R.id.tvstatus);
        radioButton.setEnabled(false);
        if (item.isChecked()) {
            radioButton.setChecked(true);
        } else {
            radioButton.setChecked(false);
        }
        //            tosettle	| [string] 待结算
//            settled	| [string]  已结清
//            owed	| [string]  欠费
//            torefund	| [string] 待退款
//            unknown	| [string]
        if (item.getSettlestate().equals("tosettle") || item.getSettlestate().equals("owed")) {
            Glide.with(AppUtils.getApp()).load(R.mipmap.ic_unpaid).into(tvstatus);
        } else if (item.getSettlestate().equals("settled")) {
            Glide.with(AppUtils.getApp()).load(R.mipmap.ic_done).into(tvstatus);
        } else {
            Glide.with(AppUtils.getApp()).load(R.mipmap.ic_outdated).into(tvstatus);
        }
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton.setChecked(radioButton.isChecked() ? false : true);
                if (radioButton.isChecked()){
                    onItemCheckListerner.onChecked(helper.getLayoutPosition());
                }
            }
        });
        if (mtag.equals(MyConstant.UNPAY_STATUS)) {
            radioButton.setVisibility(View.VISIBLE);
        } else {
            radioButton.setVisibility(View.GONE);
        }

    }


}
