package com.huiboapp.event;

import com.huiboapp.mvp.model.entity.ParkListEntity.DataBean.ResourcelistBean;

public class ParkInfoEvent {

    private ResourcelistBean resourcelistBean;

    public ParkInfoEvent(ResourcelistBean resourcelistBean) {
        this.resourcelistBean = resourcelistBean;
    }

    public ResourcelistBean getResourcelistBean() {
        return resourcelistBean;
    }
}
