package com.huiboapp.mvp.model.entity;

public class OrderDetailInfo {


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

        private String id;
        private String parksname;
        private String plate;
        private String platecolor;
        private String parkbegin;
        private String parkend;
        private int totalfee;
        private int discountfee;
        private int paidfee;
        private int refundfee;
        private String settlestate;
        private int duration;
        private String inphoto;
        private String outphoto;
        private String lat;
        private String lng;
        private String paychannel;
        private String contactphone;
        private String unpaidfee;

        public String getUnpaidfee() {
            return unpaidfee;
        }

        public void setUnpaidfee(String unpaidfee) {
            this.unpaidfee = unpaidfee;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParksname() {
            return parksname;
        }

        public void setParksname(String parksname) {
            this.parksname = parksname;
        }

        public String getPlate() {
            return plate;
        }

        public void setPlate(String plate) {
            this.plate = plate;
        }

        public String getPlatecolor() {
            return platecolor;
        }

        public void setPlatecolor(String platecolor) {
            this.platecolor = platecolor;
        }

        public String getParkbegin() {
            return parkbegin;
        }

        public void setParkbegin(String parkbegin) {
            this.parkbegin = parkbegin;
        }

        public String getParkend() {
            return parkend;
        }

        public void setParkend(String parkend) {
            this.parkend = parkend;
        }

        public int getTotalfee() {
            return totalfee;
        }

        public void setTotalfee(int totalfee) {
            this.totalfee = totalfee;
        }

        public int getDiscountfee() {
            return discountfee;
        }

        public void setDiscountfee(int discountfee) {
            this.discountfee = discountfee;
        }

        public int getPaidfee() {
            return paidfee;
        }

        public void setPaidfee(int paidfee) {
            this.paidfee = paidfee;
        }

        public int getRefundfee() {
            return refundfee;
        }

        public void setRefundfee(int refundfee) {
            this.refundfee = refundfee;
        }

        public String getSettlestate() {
            return settlestate;
        }

        public void setSettlestate(String settlestate) {
            this.settlestate = settlestate;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getInphoto() {
            return inphoto;
        }

        public void setInphoto(String inphoto) {
            this.inphoto = inphoto;
        }

        public String getOutphoto() {
            return outphoto;
        }

        public void setOutphoto(String outphoto) {
            this.outphoto = outphoto;
        }

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

        public String getPaychannel() {
            return paychannel;
        }

        public void setPaychannel(String paychannel) {
            this.paychannel = paychannel;
        }

        public String getContactphone() {
            return contactphone;
        }

        public void setContactphone(String contactphone) {
            this.contactphone = contactphone;
        }
    }
}
