package com.huiboapp.mvp.ui.widget.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.commonlib.agentweb.utils.AppUtils;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.OrderDetailInfo;

public class OrderDetailPopWindow extends PopupWindow {

    public interface OnPayListener{
        void onPay();
    }
    private OnPayListener payListener;

    public void setPayListener(OnPayListener payListener) {
        this.payListener = payListener;
    }

    public OrderDetailPopWindow(Context context, View mMenuView, OrderDetailInfo detailInfo) {
        super(context);
        this.setContentView(mMenuView);

        TextView tvorderno = mMenuView.findViewById(R.id.tvorderno);
        TextView tvcarno = mMenuView.findViewById(R.id.tvcarno);
        TextView tvaddr = mMenuView.findViewById(R.id.tvaddr);
        TextView tvstarttime = mMenuView.findViewById(R.id.tvstarttime);
        TextView tvendtime = mMenuView.findViewById(R.id.tvendtime);
        TextView tvtotaltime = mMenuView.findViewById(R.id.tvtotaltime);
        TextView tvfee = mMenuView.findViewById(R.id.tvfee);
        ImageView ivcar = mMenuView.findViewById(R.id.ivcar);
        TextView submit = mMenuView.findViewById(R.id.submit);
        tvorderno.setText(detailInfo.getData().getId());
        tvcarno.setText(detailInfo.getData().getPlate());
        tvaddr.setText(detailInfo.getData().getLat());// TODO: 2020/12/5
        tvstarttime.setText(detailInfo.getData().getParkbegin());
        tvendtime.setText(detailInfo.getData().getParkend());
        tvtotaltime.setText(detailInfo.getData().getDuration()+"");
        tvfee.setText(detailInfo.getData().getTotalfee()+"");
        Glide.with(AppUtils.getApp()).load(detailInfo.getData().getOutphoto()).into(ivcar);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payListener.onPay();
            }
        });

        //为choosePicPopWindow设置底部菜单视图
        //设置宽度
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置高度
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //是否可点击
        this.setFocusable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.half_trans));
        //设置弹出窗口为半透明
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });
    }
}
