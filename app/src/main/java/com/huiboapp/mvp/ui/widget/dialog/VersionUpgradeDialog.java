package com.huiboapp.mvp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.mvp.model.cache.UserInfoHelper;

/**
 * App下载
 * <p>
 * Created by yaojian on 2019/3/13 12:27
 */
public class VersionUpgradeDialog extends Dialog {
    private View mView;

    private TextView tvMsg;
    private TextView tvCancel;
    private TextView tvConfirm;
    private LinearLayout llayoutIgnoreVer;
    private ImageView ivIgnoreVer;

    private boolean sel = false;
    private String version;
    private String upgradeTips;

    public VersionUpgradeDialog(Context context, String version, String tips) {
        super(context, R.style.CustomProgressDialog);
        this.version = version;
        this.upgradeTips = tips;
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        setCanceledOnTouchOutside(false);
    }

    private void init() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_version_upgrade, null, false);
        tvMsg = mView.findViewById(R.id.tvMsg);
        tvCancel = mView.findViewById(R.id.tvCancel);
        tvConfirm = mView.findViewById(R.id.tvConfirm);
        if (!upgradeTips.isEmpty()) {
            tvMsg.setText(upgradeTips);
        }
        llayoutIgnoreVer = mView.findViewById(R.id.llayoutIgnoreVer);
        ivIgnoreVer = mView.findViewById(R.id.ivIgnoreVer);

        llayoutIgnoreVer.setOnClickListener(v -> {
            if (sel) {
                sel = false;
                ivIgnoreVer.setImageResource(R.mipmap.ic_check_no);
            } else {
                sel = true;
                ivIgnoreVer.setImageResource(R.mipmap.ic_check_sel);
            }
        });

        tvCancel.setOnClickListener(v -> dismiss());
    }

    public void setCancelListener(View.OnClickListener listener) {
        tvCancel.setOnClickListener(listener);
    }

    public void setConfirmListener(View.OnClickListener listener) {
        tvConfirm.setOnClickListener(listener);
    }
}
