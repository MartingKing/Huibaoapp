package com.huiboapp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.PjbTextUtils;
import com.huiboapp.R;
import com.huiboapp.app.utils.Utils;
import com.huiboapp.mvp.model.entity.HomeBannerIconEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * tab
 * <p>
 * Created by yaojian on 2018/11/22 09:47
 */
public class DectactorFooterAdapter extends BaseQuickAdapter<HomeBannerIconEntity.TabMatchBottomPageBean, BaseViewHolder> {

    private List<HomeBannerIconEntity.TabMatchBottomPageBean> mDatas;
    public DectactorFooterAdapter(@Nullable List<HomeBannerIconEntity.TabMatchBottomPageBean> data) {
        super(R.layout.item_dector_footer, data);
        this.mDatas = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBannerIconEntity.TabMatchBottomPageBean item) {
        ViewGroup.LayoutParams layoutParams = helper.getView(R.id.iv_zhima).getLayoutParams();
        if (mDatas!=null) {
            layoutParams.width = (int) ((ArmsUtils.getScreenWidth(Utils.getApp()) - DeviceUtils.dpToPixel(Utils.getApp(), 60)) / mDatas.size());
            helper.getView(R.id.iv_zhima).setLayoutParams(layoutParams);
        }
        ImageView ivProductImg = helper.itemView.findViewById(R.id.iv_zhima);
        Picasso.get().load(PjbTextUtils.getDefaultText(item.getResourcePic(), "http://m3.yinpiaobao.cn/daichao/dev/201903/20190328/153426223.png"))
                .into(ivProductImg);

    }
}
