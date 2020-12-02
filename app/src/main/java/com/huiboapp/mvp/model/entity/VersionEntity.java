package com.huiboapp.mvp.model.entity;

/**
 * 版本信息
 * <p>
 * Created by yaojian on 2019/3/12 16:54
 */
public class VersionEntity {

    /**
     * version : 104
     * url :
     * isUpdate : 1
     */

    private String version;
    private String url;
    private String tips = "";
    private int isUpdate;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    @Override
    public String toString() {
        return "VersionEntity{" +
                "version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", isUpdate=" + isUpdate +
                '}';
    }
}
