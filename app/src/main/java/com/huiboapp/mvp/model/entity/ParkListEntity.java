package com.huiboapp.mvp.model.entity;

import java.util.List;


public class ParkListEntity {


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
        private List<ResourcelistBean> resourcelist;

        public List<ResourcelistBean> getResourcelist() {
            return resourcelist;
        }

        public void setResourcelist(List<ResourcelistBean> resourcelist) {
            this.resourcelist = resourcelist;
        }

        public static class ResourcelistBean {
            /**
             * parksid : AXPXp6SBmxBpYADjYN3zTHpx
             * parksname : 百合家园停车场
             * parkstype : business
             * address : 百合家园停车场
             * opertime : null
             * charge : true
             * feedes :
             * geolocation : {"lat":"41.592678","lng":"120.458476"}
             * total : 30
             * free : 10
             * shared : true
             * sharedlots : 10
             * sharedfree : 10
             * distance : 0
             * isCollected : false
             */

            private String parksid;
            private String parksname;
            private String parkstype;
            private String address;
            private String opertime;
            private boolean charge;
            private String feedes;
            private GeolocationBean geolocation;
            private int total;
            private int free;
            private boolean shared;
            private int sharedlots;
            private int sharedfree;
            private int distance;
            private boolean isCollected;

            public String getParksid() {
                return parksid;
            }

            public void setParksid(String parksid) {
                this.parksid = parksid;
            }

            public String getParksname() {
                return parksname;
            }

            public void setParksname(String parksname) {
                this.parksname = parksname;
            }

            public String getParkstype() {
                return parkstype;
            }

            public void setParkstype(String parkstype) {
                this.parkstype = parkstype;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getOpertime() {
                return opertime;
            }

            public void setOpertime(String opertime) {
                this.opertime = opertime;
            }

            public boolean isCharge() {
                return charge;
            }

            public void setCharge(boolean charge) {
                this.charge = charge;
            }

            public String getFeedes() {
                return feedes;
            }

            public void setFeedes(String feedes) {
                this.feedes = feedes;
            }

            public GeolocationBean getGeolocation() {
                return geolocation;
            }

            public void setGeolocation(GeolocationBean geolocation) {
                this.geolocation = geolocation;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getFree() {
                return free;
            }

            public void setFree(int free) {
                this.free = free;
            }

            public boolean isShared() {
                return shared;
            }

            public void setShared(boolean shared) {
                this.shared = shared;
            }

            public int getSharedlots() {
                return sharedlots;
            }

            public void setSharedlots(int sharedlots) {
                this.sharedlots = sharedlots;
            }

            public int getSharedfree() {
                return sharedfree;
            }

            public void setSharedfree(int sharedfree) {
                this.sharedfree = sharedfree;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public boolean isIsCollected() {
                return isCollected;
            }

            public void setIsCollected(boolean isCollected) {
                this.isCollected = isCollected;
            }

            public static class GeolocationBean {
                /**
                 * lat : 41.592678
                 * lng : 120.458476
                 */

                private String lat;
                private String lng;

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }
            }
        }
    }
}
