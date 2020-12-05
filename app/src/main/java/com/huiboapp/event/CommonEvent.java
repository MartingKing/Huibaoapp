package com.huiboapp.event;

import com.huiboapp.mvp.model.entity.UserInfoEntity.CarList;

import java.util.List;

;

public class CommonEvent {

    public CommonEvent(List<CarList> platelist) {
        this.platelist = platelist;
    }

    private List<CarList> platelist;

    public List<CarList> getPlatelist() {
        return platelist;
    }

}
