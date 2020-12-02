package com.huiboapp.mvp.model.entity;

/**
 * 图形验证码实体类
 */
public class ImageCodeEntity {


    private String ackmsg;
    private String ackmsgid;
    private int result;
    private String reason;
    private boolean matched;

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

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

    @Override
    public String toString() {
        return "ImageCodeEntity{" +
                "ackmsg='" + ackmsg + '\'' +
                ", ackmsgid='" + ackmsgid + '\'' +
                ", result=" + result +
                ", reason='" + reason + '\'' +
                ", matched=" + matched +
                '}';
    }
}
