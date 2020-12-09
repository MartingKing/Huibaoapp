package com.huiboapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;
import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.model.entity.ParkListEntity.DataBean.ResourcelistBean;


public class FindParkAdapter extends BaseQuickAdapter<ResourcelistBean, BaseViewHolder> {


    public FindParkAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResourcelistBean item) {
        helper.setText(R.id.tvAddr, item.getParksname());
        helper.setText(R.id.tvdetail, item.getAddress());
        helper.setText(R.id.distance, HBTUtls.getKm(item.getDistance()));
    }


}
