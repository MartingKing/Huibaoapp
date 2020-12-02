package com.mmb.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mmb.R;


/**
 * Created by Administrator on 2018/6/11.
 */

public class DialogUtil extends Dialog implements View.OnClickListener {
    private TextView tv_gallery;
    private TextView tv_camera;
    private TextView tv_cancel;

    private Context mContext;
    private OnCloseListener mListener;
    private boolean mCancelable;

    public DialogUtil(Context context, boolean cancelable, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mListener = listener;
        this.mCancelable = cancelable;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jjd_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        tv_gallery = findViewById(R.id.tv_gallery);
        tv_camera = findViewById(R.id.tv_camera);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_gallery.setOnClickListener(this);
        tv_camera.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        if (!mCancelable) {
            this.setCancelable(false);
            this.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_gallery) {
            if (mListener != null) {
                mListener.onGalleryClicked();
                this.dismiss();
            }

        } else if (i == R.id.tv_camera) {
            if (mListener != null) {
                mListener.onCameraClicked();
                this.dismiss();
            }

        } else if (i == R.id.tv_cancel) {
            if (mListener != null) {
                this.dismiss();
            }

        } else {
        }
    }

    public interface OnCloseListener {
        void onGalleryClicked();

        void onCameraClicked();
    }
}
