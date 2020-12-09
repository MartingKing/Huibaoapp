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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.ParkListEntity;
import com.huiboapp.mvp.ui.adapter.FindParkAdapter;

public class FindParkPopWindow extends PopupWindow {
    public interface ParkPopWindowItemClickListerner {
        void onClick(BaseQuickAdapter adapter, View view, int position);
    }

    private ParkPopWindowItemClickListerner clickListerner;

    public void setClickListerner(ParkPopWindowItemClickListerner clickListerner) {
        this.clickListerner = clickListerner;
    }

    public FindParkPopWindow(Context context, View mMenuView, ParkListEntity parkListEntit) {
        super(context);
        this.setContentView(mMenuView);
        RecyclerView recyclerview = mMenuView.findViewById(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        FindParkAdapter findParkAdapter = new FindParkAdapter(R.layout.item_findpark);
        if (parkListEntit != null) {
            findParkAdapter.addData(parkListEntit.getData().getResourcelist());
            recyclerview.setAdapter(findParkAdapter);
            findParkAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    dismiss();
                    clickListerner.onClick(adapter, view, position);
                }
            });
        }

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
