package com.huiboapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.ProblemEntity;

/**
 * 常见问题列表
 *
 * Created by yaojian on 2018/11/22 09:47
 */
public class ProblemListAdapter extends BaseQuickAdapter<ProblemEntity, BaseViewHolder> {

    public ProblemListAdapter() {
        super(R.layout.item_problem, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProblemEntity item) {
        helper.setGone(R.id.ivInto1, !item.isOpen());
        helper.setGone(R.id.ivInto2, item.isOpen());
        helper.setGone(R.id.tvInfo, item.isOpen());
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvInfo, item.getInfo());
    }

}
