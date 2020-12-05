package com.huiboapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.CommonEntity;

/**
 * 产品列表
 *
 * Created by yaojian on 2018/11/22 09:47
 */
public class InterestListAdapter extends BaseQuickAdapter<CommonEntity, BaseViewHolder> {

    public InterestListAdapter() {
        super(R.layout.item_interest, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonEntity item) {

    }

}
