package com.huiboapp.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.utils.PjbTextUtils;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.LatestProEntity;
import com.squareup.picasso.Picasso;


/**
 * Created by Administrator on 2018/6/11.
 */

public class RecommendProDialogUtil extends Dialog implements View.OnClickListener {

    private Context mContext;
    private LatestProEntity mDatas;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private boolean mCancelable;

    public RecommendProDialogUtil(Context context) {
        super(context);
        this.mContext = context;
    }

    public RecommendProDialogUtil(Context context, int themeResId, LatestProEntity datas) {
        super(context, themeResId);
        this.mContext = context;
        this.mDatas = datas;
    }

    public RecommendProDialogUtil(Context context, boolean cancelable, int themeResId, LatestProEntity datas, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mDatas = datas;
        this.listener = listener;
        this.mCancelable = cancelable;

    }

    protected RecommendProDialogUtil(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public RecommendProDialogUtil setTitle(String title) {
        this.title = title;
        return this;
    }

    public RecommendProDialogUtil setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public RecommendProDialogUtil setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_pro_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        TextView titleTxt = findViewById(R.id.title);
        TextView submitTxt = findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        TextView cancelTxt = findViewById(R.id.cancel);
        View divider = findViewById(R.id.middle_divider);
        cancelTxt.setOnClickListener(this);

        ImageView icon = findViewById(R.id.ivProductImg);
        TextView name = findViewById(R.id.ivProductName);
        TextView interest = findViewById(R.id.ivProductInterest);
        TextView desc = findViewById(R.id.tvProductInfo);
        Picasso.get().load(PjbTextUtils.getDefaultText(mDatas.getProductImg(),"http://m3.yinpiaobao.cn/daichao/dev/201903/20190328/153426223.png")).into(icon);
        name.setText(mDatas.getProductName());
        interest.setText(String.valueOf(mDatas.getMonthRate())+"%");
        desc.setText(mDatas.getSubTitle());

        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }
        if (!mCancelable) {
            cancelTxt.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
            submitTxt.setBackgroundResource(R.drawable.round_shape_bottom);
        } else {
            cancelTxt.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;
            default:
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}
