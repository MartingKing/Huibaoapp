package com.huiboapp.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.huiboapp.mvp.ui.widget.dialog.CustomProgressDialog;

/**
 * Created by Vector on 2016/8/12 0012.
 */
public class HProgressDialogUtils {
    private static CustomProgressDialog sHorizontalProgressDialog;

    private HProgressDialogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    @SuppressLint("NewApi")
    public static void showHorizontalProgressDialog(Context context, String msg, boolean isShowSize) {
        cancel();

        if (sHorizontalProgressDialog == null) {
            sHorizontalProgressDialog = new CustomProgressDialog(context);
            sHorizontalProgressDialog.setCancelable(false);
        }
        sHorizontalProgressDialog.show();
    }

    public static void cancel() {
        if (sHorizontalProgressDialog != null) {
            sHorizontalProgressDialog.dismiss();
            sHorizontalProgressDialog = null;
        }
    }

    public static void setProgress(int current) {
        if (sHorizontalProgressDialog == null) {
            return;
        }
        sHorizontalProgressDialog.setProgress(current);
//        if (sHorizontalProgressDialog.getProgress() >= 100) {
//            sHorizontalProgressDialog.dismiss();
//            sHorizontalProgressDialog = null;
//        }
    }

    public static void setProgress(long current) {
        if (sHorizontalProgressDialog == null) {
            return;
        }
        sHorizontalProgressDialog.setProgress(((int) current) / (1024 * 1024));
//        if (sHorizontalProgressDialog.getProgress() >= 100) {
//            sHorizontalProgressDialog.dismiss();
//            sHorizontalProgressDialog = null;
//        }
    }

}
