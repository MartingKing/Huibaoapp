package com.huiboapp.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.PjbTextUtils;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.squareup.picasso.Picasso;

/**
 * tab
 * <p>
 * Created by yaojian on 2018/11/22 09:47
 */
public class IconJumpAdapter extends BaseQuickAdapter<ProductListEntity, BaseViewHolder> {

    public IconJumpAdapter() {
        super(R.layout.item_icon_jump, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductListEntity item) {
        ImageView ivProductImg = helper.itemView.findViewById(R.id.ivProductImg);
        Picasso.get().load(PjbTextUtils.getDefaultText(item.getProductImg(), "http://m3.yinpiaobao.cn/daichao/dev/201903/20190328/153426223.png"))
                .resize(50, 50)
                .centerCrop()
                .into(ivProductImg);

        helper.setText(R.id.tvProductName, item.getProductName());
        helper.setText(R.id.tv_profit_desc, item.getSubTitle());
        helper.setText(R.id.tv_amount, PjbTextUtils.getThreeAppendText(PjbTextUtils.getAmountByUnit(item.getLoanMin()), "-", PjbTextUtils.getAmountByUnit(item.getLoanMax())));
        helper.setText(R.id.tv_month_profit, PjbTextUtils.getTwoPoint(Double.valueOf(item.getMonthRate())) + "%");
        helper.setText(R.id.tv_month_limit,
                new StringBuffer().append(PjbTextUtils.getDefaultText(item.getTermStart(), ""))
                        .append("-")
                        .append(PjbTextUtils.getDefaultText(item.getTermEnd(), "0"))
                        .append("个月"));
    }
}
