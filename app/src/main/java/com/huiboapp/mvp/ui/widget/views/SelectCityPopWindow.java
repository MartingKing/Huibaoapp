package com.huiboapp.mvp.ui.widget.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.huiboapp.R;
import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.ui.adapter.SelectCityAdapter;

import java.util.List;

public class SelectCityPopWindow extends PopupWindow {
    public interface SelectCityListener {
        void onClick(String city);
    }

    private SelectCityListener clickListerner;

    public void setClickListerner(SelectCityListener clickListerner) {
        this.clickListerner = clickListerner;
    }

    public SelectCityPopWindow(Context context, View mMenuView) {
        super(context);
        this.setContentView(mMenuView);
        RecyclerView recyclerview = mMenuView.findViewById(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        SelectCityAdapter findParkAdapter = new SelectCityAdapter(R.layout.item_selectcity);
        List<String> city = HBTUtls.getCity();
        findParkAdapter.addData(city);
        recyclerview.setAdapter(findParkAdapter);
        findParkAdapter.setCitySelectedListerner(new SelectCityAdapter.OnCitySelectedListerner() {
            @Override
            public void onCLick(String city) {
                clickListerner.onClick(city);
                dismiss();
            }
        });
        //为choosePicPopWindow设置底部菜单视图
        //设置宽度
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置高度
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //是否可点击
        this.setFocusable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        this.setOutsideTouchable(true);
        this.setTouchable(true);
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
