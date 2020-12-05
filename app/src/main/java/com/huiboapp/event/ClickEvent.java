package com.huiboapp.event;

public class ClickEvent {
    private int param;

    public ClickEvent(int height) {
        this.param = height;
    }

    public int getHeight() {
        return param;
    }
}
