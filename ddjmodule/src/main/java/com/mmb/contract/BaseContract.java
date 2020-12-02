package com.mmb.contract;

import android.annotation.TargetApi;
import android.os.Build;

import com.mmb.view.TopTitleBar;


/**
 * Created by lwc on 2019/6/26.
 */
public interface BaseContract {

    interface BaseView {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        void setStatusBarColor(int statusColor);
        TopTitleBar initIndicate();
        /**恢复原始的系统title*/
        void resetSystemTitleBar();
    }
    interface MainView{

    }
}
