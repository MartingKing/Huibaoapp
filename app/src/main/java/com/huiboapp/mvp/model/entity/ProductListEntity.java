package com.huiboapp.mvp.model.entity;

import java.io.Serializable;

/**
 * 产品列表
 */
public class ProductListEntity implements Serializable {

    /**
     * id : 94
     * productName : 贷款产品四号
     * subTitle : 1234567891011111
     * productLabel : 3
     * monthRate : 36
     * termStart : null
     * termEnd : null
     * loanMin : 5000
     * loanMax : 10000
     */

    private int id;
    private String productName;
    private String productUrl;
    private String productImg;
    private String subTitle;
    private String productLabel;
    private String monthRate;
    private String termStart;
    private String termEnd;
    private String loanMin;
    private String loanMax;

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public String getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(String monthRate) {
        this.monthRate = monthRate;
    }

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

    public String getLoanMin() {
        return loanMin;
    }

    public void setLoanMin(String loanMin) {
        this.loanMin = loanMin;
    }

    public String getLoanMax() {
        return loanMax;
    }

    public void setLoanMax(String loanMax) {
        this.loanMax = loanMax;
    }

    @Override
    public String toString() {
        return "ProductListEntity{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productUrl='" + productUrl + '\'' +
                ", productImg='" + productImg + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", productLabel='" + productLabel + '\'' +
                ", monthRate='" + monthRate + '\'' +
                ", termStart='" + termStart + '\'' +
                ", termEnd='" + termEnd + '\'' +
                ", loanMin='" + loanMin + '\'' +
                ", loanMax='" + loanMax + '\'' +
                '}';
    }
}
