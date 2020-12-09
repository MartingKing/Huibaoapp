package com.huiboapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;


public class MyPresevateAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public MyPresevateAdapter() {
        super(R.layout.item_presevation);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvcarnum, "黑A58M6" + item);
    }

}
