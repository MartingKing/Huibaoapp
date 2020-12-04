package com.huiboapp.mvp.model.entity;


public class WelcomeEntity {

    private String slash;

    @Override
    public String toString() {
        return "WelcomeEntity{" +
                "slash='" + slash + '\'' +
                '}';
    }

    public String getSlash() {
        return slash;
    }

    public void setSlash(String slash) {
        this.slash = slash;
    }
}
