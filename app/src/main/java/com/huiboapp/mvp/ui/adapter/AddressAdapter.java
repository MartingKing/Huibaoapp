package com.huiboapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.AddressItemEntity;

/**
 * Created by yaojian on 2018/11/23 11:26
 */
public class AddressAdapter extends BaseQuickAdapter<AddressItemEntity, BaseViewHolder> {

    public AddressAdapter() {
        super(R.layout.item_address, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressItemEntity item) {
        helper.setText(R.id.tvAddressName, item.getN());
    }

}
