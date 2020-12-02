package com.huiboapp.mvp.model.entity;

/**
 * 常见问题
 */
public class ProblemEntity {
    private int title;
    private int info;
    private boolean open = false;

    public ProblemEntity() {
    }

    public ProblemEntity(int title, int info) {
        this.title = title;
        this.info = info;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
