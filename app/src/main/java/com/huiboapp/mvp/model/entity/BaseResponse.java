package com.huiboapp.mvp.model.entity;

import java.io.Serializable;

/**
 * Created by yaojian on 2019/1/28 10:43
 */
public class BaseResponse<T> implements Serializable {

    private String ackmsg = "";
    private String result = "";
    private String reason = "";
    private T data;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "ackmsg='" + ackmsg + '\'' +
                ", result='" + result + '\'' +
                ", reason='" + reason + '\'' +
                ", data=" + data +
                '}';
    }

    public String getAckmsg() {
        return ackmsg;
    }

    public void setAckmsg(String ackmsg) {
        this.ackmsg = ackmsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * 请求是否成功
     */
    public boolean isSuccess() {
        return result.equals("300000");
    }

    public boolean isTokenAvalable() {
        return !result.equals("300100");
    }
}
