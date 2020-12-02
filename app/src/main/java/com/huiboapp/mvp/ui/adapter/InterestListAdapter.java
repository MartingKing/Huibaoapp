package com.huiboapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;
import com.huiboapp.app.utils.NumberUtils;
import com.huiboapp.mvp.model.entity.InterestEntity;

/**
 * 产品列表
 *
 * Created by yaojian on 2018/11/22 09:47
 */
public class InterestListAdapter extends BaseQuickAdapter<InterestEntity, BaseViewHolder> {

    public InterestListAdapter() {
        super(R.layout.item_interest, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, InterestEntity item) {
        helper.setText(R.id.tvNum, item.getNum() + "");
        helper.setText(R.id.tvPrincipal, "¥" + NumberUtils.roundDownStr(2, item.getPrincipal() + ""));
        helper.setText(R.id.tvInterest, "¥" + NumberUtils.roundDownStr(2, item.getInterest() + ""));
        helper.setText(R.id.tvAmount, "¥" + NumberUtils.roundDownStr(2, item.getAmount() + ""));
    }

}
