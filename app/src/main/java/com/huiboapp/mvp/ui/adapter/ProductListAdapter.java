package com.huiboapp.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.PjbTextUtils;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 产品列表
 * <p>
 * Created by yaojian on 2018/11/22 09:47
 */
public class ProductListAdapter extends BaseQuickAdapter<ProductListEntity, BaseViewHolder> {

    public ProductListAdapter() {
        super(R.layout.item_product, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductListEntity item) {
        ImageView ivProductImg = helper.getView(R.id.ivProductImg);
        Picasso.get().load(PjbTextUtils.getDefaultText(item.getProductImg(), "http://m3.yinpiaobao.cn/daichao/dev/201903/20190328/153426223.png"))
                .resize(50, 50)
                .centerCrop()
                .into(ivProductImg);
        helper.setText(R.id.ivProductName, item.getProductName());
        helper.setText(R.id.ivProductDesc, item.getSubTitle());
        //标签 1低利息，2大额贷 ，3小额贷  4闪电放款，5最新上线，6仅需芝麻分，7仅需身份证
        TextView sin1 = helper.getView(R.id.tv_sing);
        TextView sin2 = helper.getView(R.id.tv_sing2);
        if (item.getProductLabel().contains(",")) {
            String[] labelList = item.getProductLabel().split(",");
            List<Integer> mDatas = new ArrayList<>();
            for (String aLabelList : labelList) {
                if (Integer.valueOf(aLabelList) < 4)
                    mDatas.add(Integer.valueOf(aLabelList));
            }
            if (mDatas.size() > 1) {
                Collections.sort(mDatas);
                sin1.setText(getSingleLable(String.valueOf(mDatas.get(0)), sin1));
                sin2.setText(getSingleLable(String.valueOf(mDatas.get(1)), sin2));
            } else {
                sin1.setText(getSingleLable(String.valueOf(mDatas.get(0)), sin1));
            }
        } else {
            if (!item.getProductLabel().isEmpty()) {
                sin1.setText(getSingleLable(item.getProductLabel(), sin1));
            }
        }
        helper.setText(R.id.tvProductMaximumQuota,
                new StringBuffer().append("参考日利率:")
                        .append(PjbTextUtils.getTwoPoint(Double.valueOf(PjbTextUtils.getDefaultText(item.getMonthRate(), "0"))))
                        .append("%").append(" | ")
                        .append(PjbTextUtils.getDefaultText(item.getTermStart(), "0"))
                        .append("-")
                        .append(PjbTextUtils.getDefaultText(item.getTermEnd(), "0"))
                        .append("个月"));
        helper.setText(R.id.tvAmount, PjbTextUtils.getAmountByUnit(item.getLoanMin()) + "-" + PjbTextUtils.getAmountByUnit(item.getLoanMax()));
    }

    private String getSingleLable(String str, TextView view) {
        if (Integer.valueOf(str) < 4) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        String label = "";
        switch (str) {
            case "1":
                label = "低利息";
                break;
            case "2":
                label = "大额贷";
                break;
            case "3":
                label = "小额贷";
                break;
            default:
                break;
        }
        return label;
    }
}
