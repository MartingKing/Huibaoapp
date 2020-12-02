package com.mmb.bridge.widgetinterface;


import com.mmb.contract.BaseContract;

/**
 * Created by lwc on 2019/6/26.
 */
public interface DdjWidget {
    void handle(BaseContract.BaseView mView, String data);

    String getType();
}
