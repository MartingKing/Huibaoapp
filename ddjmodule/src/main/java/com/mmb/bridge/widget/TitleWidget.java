package com.mmb.bridge.widget;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mmb.bridge.widgetinterface.DdjWidget;
import com.mmb.contract.BaseContract;
import com.mmb.view.TopTitleBar;

/**
 * Created by lwc on 2019/6/26.
 */
public class TitleWidget implements DdjWidget {


    private TopTitleBar topTitleBar;

    @Override
    public void handle(BaseContract.BaseView mView, String data) {
        TopTitleBar.TitleFactoryBean titleFactory = null;
        try {
            titleFactory = new Gson().fromJson(data, TopTitleBar.TitleFactoryBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (titleFactory == null) return;

        if (titleFactory.getEvent() != null && getType().equals(titleFactory.getEvent().getOperation())) {
            if (titleFactory.getData() == null) {
                if (topTitleBar != null) {
                    topTitleBar.setVisibility(View.GONE);
                    mView.resetSystemTitleBar();
                }
            } else {
                topTitleBar = mView.initIndicate();
                topTitleBar.setVisibility(View.VISIBLE);
                topTitleBar.setTitleFacData(titleFactory.getData());
                if (!TextUtils.isEmpty(titleFactory.getData().getBackgroundColor())) {
                    mView.setStatusBarColor(Color.parseColor(titleFactory.getData().getBackgroundColor()));
                }
            }
        }

    }


    @Override
    public String getType() {
        return "title";
    }
}
