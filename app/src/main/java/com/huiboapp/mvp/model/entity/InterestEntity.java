package com.huiboapp.mvp.model.entity;

/**
 * todo 暂时无用的页面
 */
public class InterestEntity {

    private int num;// 期数
    private double principal;// 本金
    private double interest;// 利息
    private double amount;// 每期还款金额

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
