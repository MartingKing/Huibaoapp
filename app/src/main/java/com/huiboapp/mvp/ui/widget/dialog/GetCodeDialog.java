package com.huiboapp.mvp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.utils.ArmsUtils;
import com.huiboapp.R;

/**
 * 获取验证码
 * <p>
 * Created by yaojian on 2018/11/23 11:11
 */
public class GetCodeDialog extends Dialog {

    private View mView;
    private Context mContext;
    private Bitmap mBitmap;
    private GetCodeListener listener;

    public GetCodeDialog(@NonNull Context context, Bitmap bitmap) {
        super(context, R.style.PromptDialogStyle);
        mContext = context;
        mBitmap = bitmap;

        if (context instanceof GetCodeListener)
            listener = (GetCodeListener) context;

        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        setCanceledOnTouchOutside(true);
        // 设置显示动画
        Window window = getWindow();
        window.setWindowAnimations(R.style.bottom_show_anim_style);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.gravity = Gravity.BOTTOM;
        onWindowAttributesChanged(wl);
    }

    private void initView() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_getcode, null, false);

        LinearLayout llayoutBg = mView.findViewById(R.id.llayoutBg);
        ImageView ivClose = mView.findViewById(R.id.ivClose);

        ImageView ivCode = mView.findViewById(R.id.ivCode);
        EditText etCode = mView.findViewById(R.id.etCode);
        TextView tvCommit = mView.findViewById(R.id.tvCommit);
        Glide.with(mContext).load(mBitmap).into(ivCode);

        tvCommit.setOnClickListener(v -> {
            String imageCode = etCode.getText().toString().trim();
            if (TextUtils.isEmpty(imageCode)) {
                ArmsUtils.makeText(getContext().getApplicationContext(), "请输入图形验证码");
                return;
            }
            if (listener != null)
                listener.getImagecodeThenCheck(imageCode);
        });

        llayoutBg.setOnClickListener(v -> dismiss());
        ivClose.setOnClickListener(v -> dismiss());

        ivCode.setOnClickListener(view -> {
            if (listener != null)
                listener.getImageCode();
        });

    }

    public interface GetCodeListener {
        void getImagecodeThenCheck(String imageCode);

        void getImageCode();
    }

}
