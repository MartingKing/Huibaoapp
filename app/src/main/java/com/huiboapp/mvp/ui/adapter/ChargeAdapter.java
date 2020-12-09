package com.huiboapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;


public class ChargeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ChargeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_amount,item);
    }


}
