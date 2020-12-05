package com.huiboapp.mvp.ui.widget.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.huiboapp.R;

public class ChoosePicPopWindow extends PopupWindow implements View.OnClickListener {
    private Button takePhotoBtn, pickPhotoBtn, cancelBtn;
    private View mMenuView;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takePhotoBtn:
                mSelectListerner.takephoto();
                dismiss();
                break;
            case R.id.pickPhotoBtn:
                mSelectListerner.selecpic();
                dismiss();
                break;
            case R.id.cancelBtn:
                dismiss();
                break;
        }
    }

    public interface SelectPicListerner {
        void takephoto();

        void selecpic();
    }

    private SelectPicListerner mSelectListerner;

    @SuppressLint("InflateParams")
    public ChoosePicPopWindow(Context context, SelectPicListerner listerner) {
        super(context);
        this.mSelectListerner = listerner;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_select_gallary, null);
        takePhotoBtn = mMenuView.findViewById(R.id.takePhotoBtn);
        pickPhotoBtn = mMenuView.findViewById(R.id.pickPhotoBtn);
        cancelBtn = mMenuView.findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(this);
        pickPhotoBtn.setOnClickListener(this);
        takePhotoBtn.setOnClickListener(this);

        //为choosePicPopWindow设置底部菜单视图
        this.setContentView(mMenuView);
        //设置宽度
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置高度
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //是否可点击
        this.setFocusable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        //设置弹出窗口为半透明
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
