package com.huiboapp.mvp.ui.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.huiboapp.R;
import com.huiboapp.app.utils.Utils;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity.IconBean;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 首页icon列表
 * <p>
 * Created by yaojian on 2018/11/22 09:47
 */
public class HomeIconListAdapter extends BaseQuickAdapter<IconBean, BaseViewHolder> {

    private List<IconBean> mDatas;

    public HomeIconListAdapter(int layoutResId, @Nullable List<IconBean> data) {
        super(layoutResId, data);
        this.mDatas = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, IconBean item) {
        ViewGroup.LayoutParams layoutParams = helper.getView(R.id.item_icon).getLayoutParams();
        if (mDatas!=null) {
            layoutParams.width = (int) ((ArmsUtils.getScreenWidth(Utils.getApp()) - DeviceUtils.dpToPixel(Utils.getApp(), 58)) / mDatas.size());//
            helper.getView(R.id.item_icon).setLayoutParams(layoutParams);
        }
        ImageView imageView = helper.getView(R.id.item_img);
        Picasso.get().load(item.getResourcePic())
                .fit()
                .placeholder(R.mipmap.background_no_data)
                .error(R.mipmap.background_no_data)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .into(imageView);

        helper.setText(R.id.item_desc, item.getResourceTitle());
    }

}
