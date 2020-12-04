package com.huiboapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;


public class MyCarsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MyCarsAdapter() {
        super(R.layout.item_my_cars, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_check_time, "距离车检还有" + item + "天");
    }

}
