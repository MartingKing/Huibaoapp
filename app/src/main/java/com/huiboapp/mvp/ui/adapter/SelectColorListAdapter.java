package com.huiboapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;


public class SelectColorListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SelectColorListAdapter() {
        super(R.layout.item_select_color, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_txt_color, item);
    }
}
