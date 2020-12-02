package com.huiboapp.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 产品列表
 *
 * Created by yaojian on 2018/11/22 09:47
 */
public class RecProductList2Adapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public RecProductList2Adapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
