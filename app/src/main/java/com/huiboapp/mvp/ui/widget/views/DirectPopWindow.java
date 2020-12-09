package com.huiboapp.mvp.ui.widget.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.model.entity.ParkListEntity;

public class DirectPopWindow extends PopupWindow {
    public interface DirectPopWindowClickListerner {
        void onShareClick();

        void onDirectClick();
    }

    private DirectPopWindowClickListerner directPopWindowClickListerner;

    public void setDirectPopWindowClickListerner(DirectPopWindowClickListerner directPopWindowClickListerner) {
        this.directPopWindowClickListerner = directPopWindowClickListerner;
    }

    public DirectPopWindow(Context context, View mMenuView, ParkListEntity.DataBean.ResourcelistBean datas,String btText) {
        super(context);
        this.setContentView(mMenuView);
        TextView tvAddres = mMenuView.findViewById(R.id.tvAddres);
        TextView tv_addres_desc = mMenuView.findViewById(R.id.tv_addres_desc);
        TextView tv_distance = mMenuView.findViewById(R.id.tv_distance);
        TextView tv_rest_park = mMenuView.findViewById(R.id.tv_rest_park);
        Button share = mMenuView.findViewById(R.id.share);
        Button submit = mMenuView.findViewById(R.id.submit);
        submit.setText(btText);
        if (datas != null) {
            tvAddres.setText(datas.getParksname());
            tv_addres_desc.setText(datas.getAddress());
            tv_rest_park.setText(datas.getTotal() + "");
            tv_distance.setText("距离：" + HBTUtls.getKm(datas.getDistance()));
        }
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directPopWindowClickListerner.onShareClick();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (directPopWindowClickListerner != null)
                    directPopWindowClickListerner.onDirectClick();
            }
        });
        //为choosePicPopWindow设置底部菜单视图
        //设置宽度
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置高度
        this.setHeight(1000);
        //是否可点击
        this.setFocusable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        //实例化一个颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.transparent));
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
