package com.huiboapp.mvp.model.entity;

/**
 * 通用布尔值实体类
 */
public class CommonBooleanEntity {

    private String message = "";
    private String status = "";

    private boolean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
