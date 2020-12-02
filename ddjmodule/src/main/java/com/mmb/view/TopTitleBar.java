package com.mmb.view;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmb.R;
import com.mmb.bean.BaseJsBean;

/**
 * Created by lwc on 2019/6/26.
 */
public class TopTitleBar extends RelativeLayout {

    View view;
    ImageView leftIv;
    TextView titleTv;
    TextView tvRight;
    ConstraintLayout containerRl;
    View immersiveCompatView;

    public TopTitleBar(Context context) {
        super(context);
        init(context);
    }

    public TopTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.view_top_title_bar, this, true);
        leftIv = view.findViewById(R.id.leftIv);
        titleTv = view.findViewById(R.id.titleTv);
        tvRight = view.findViewById(R.id.tvRight);
        containerRl = view.findViewById(R.id.containerRl);
        immersiveCompatView = view.findViewById(R.id.immersiveCompatView);
//        initListener();
    }


    public void setTitleFacData(TitleFactory data) {
        if (!TextUtils.isEmpty(data.titleMsg)){
            setTitle(data.titleMsg);
        }
        if (!TextUtils.isEmpty(data.backgroundColor)){
            setBackgroundColor(Color.parseColor(data.backgroundColor));
        }
        if (!TextUtils.isEmpty(data.titleTextColor)){
            setTitleTextColor(Color.parseColor(data.titleTextColor));
        }
        if (data.leftBackButtonDisplay){
            setLeftIvVisibility(View.VISIBLE);
        }else {
            setLeftIvVisibility(View.GONE);
        }
    }

    public static class TitleFactory {
        /** titlebar背景颜色 */
        String backgroundColor;
        /** title*/
        String titleMsg ;
        /** title 颜色*/
        String titleTextColor;
        /** 是否展示左边返回按钮*/
        boolean leftBackButtonDisplay;

        public String getBackgroundColor() {
            return backgroundColor;
        }
    }

    public static class TitleFactoryBean extends BaseJsBean {
        private TitleFactory data;

        public TitleFactory getData() {
            return data;
        }
    }

    public void setLeftIvListener(final TopBarListener mTopBarListener) {
        leftIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTopBarListener != null){
                    mTopBarListener.onRightIvListener(view);
                }
            }
        });
    }

    public void setImmersiveCompatViewHeight(int height){
        immersiveCompatView.getLayoutParams().height = height;
    }

    public void setLeftIvVisibility(int visibility){
        leftIv.setVisibility(visibility);
    }
    public void setTvRightVisibility(int visibility){
        tvRight.setVisibility(visibility);
    }

    public void setTitle(String msg){
        titleTv.setText(msg);
    }
    public void setTitleTextColor(int color){
        titleTv.setTextColor(color);
    }
    public void setBackgroundColor(int color){
        containerRl.setBackgroundColor(color);
    }
    public interface TopBarListener{
        void onRightIvListener(View view);
    }
}
