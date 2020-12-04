package com.huiboapp.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 首页资源实体类
 */
public class HomeBannerIconEntity implements Serializable {

    private String ackmsg;
    private String ackmsgid;
    private int result;
    private String reason;
    private DataBean data;

    public String getAckmsg() {
        return ackmsg;
    }

    public void setAckmsg(String ackmsg) {
        this.ackmsg = ackmsg;
    }

    public String getAckmsgid() {
        return ackmsgid;
    }

    public void setAckmsgid(String ackmsgid) {
        this.ackmsgid = ackmsgid;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> mainpageslide;

        public List<String> getMainpageslide() {
            return mainpageslide;
        }

        public void setMainpageslide(List<String> mainpageslide) {
            this.mainpageslide = mainpageslide;
        }
    }
}
