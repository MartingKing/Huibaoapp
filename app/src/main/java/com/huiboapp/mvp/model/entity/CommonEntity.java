package com.huiboapp.mvp.model.entity;


public class CommonEntity {

    private String ackmsg;
    private String ackmsgid;
    private int result;
    private String reason;
    private boolean matched;
    int id;

    @Override
    public String toString() {
        return "CommonEntity{" +
                "ackmsg='" + ackmsg + '\'' +
                ", ackmsgid='" + ackmsgid + '\'' +
                ", result=" + result +
                ", reason='" + reason + '\'' +
                ", matched=" + matched +
                ", id=" + id +
                '}';
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

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
