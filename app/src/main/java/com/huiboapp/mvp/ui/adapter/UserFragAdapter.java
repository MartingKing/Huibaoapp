package com.huiboapp.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.commonlib.agentweb.utils.AppUtils;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.UserFragEntity;


public class UserFragAdapter extends BaseQuickAdapter<UserFragEntity, BaseViewHolder> {

    public UserFragAdapter() {
        super(R.layout.item_usr_frag, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserFragEntity item) {
        helper.setText(R.id.tvdesc, item.getIconname());
        ImageView imageView = helper.getView(R.id.ivProductImg);
        Glide.with(AppUtils.getApp()).load(item.getIcon()).into(imageView);
    }

}
