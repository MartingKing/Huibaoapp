package com.huiboapp.mvp.model.entity;

public class NewTruggleEntity {

    private boolean auditSwitch;
    private String redirectType;
    private int ifOpenCashLoanSwitch;//是否开启现金贷H5配置 ,0 未开启，1开启
    private String mmbBorrowingBtnUrl;//现金贷H5配置开启后，跳转到现金贷H5页面的“立即借款”按钮的URL

    public int getIfOpenCashLoanSwitch() {
        return ifOpenCashLoanSwitch;
    }

    public void setIfOpenCashLoanSwitch(int ifOpenCashLoanSwitch) {
        this.ifOpenCashLoanSwitch = ifOpenCashLoanSwitch;
    }

    public String getMmbBorrowingBtnUrl() {
        return mmbBorrowingBtnUrl;
    }

    public void setMmbBorrowingBtnUrl(String mmbBorrowingBtnUrl) {
        this.mmbBorrowingBtnUrl = mmbBorrowingBtnUrl;
    }

    public boolean isAuditSwitch() {
        return auditSwitch;
    }

    public void setAuditSwitch(boolean auditSwitch) {
        this.auditSwitch = auditSwitch;
    }

    public String getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(String redirectType) {
        this.redirectType = redirectType;
    }

    @Override
    public String toString() {
        return "NewTruggleEntity{" +
                "auditSwitch=" + auditSwitch +
                ", redirectType='" + redirectType + '\'' +
                ", ifOpenCashLoanSwitch=" + ifOpenCashLoanSwitch +
                ", mmbBorrowingBtnUrl='" + mmbBorrowingBtnUrl + '\'' +
                '}';
    }
}
