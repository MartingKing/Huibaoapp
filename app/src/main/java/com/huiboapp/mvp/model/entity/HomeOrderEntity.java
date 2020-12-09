package com.huiboapp.mvp.model.entity;

import java.util.List;

public class HomeOrderEntity {


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
        private List<OrderlistBean> orderlist;

        public List<OrderlistBean> getOrderlist() {
            return orderlist;
        }

        public void setOrderlist(List<OrderlistBean> orderlist) {
            this.orderlist = orderlist;
        }

        public static class OrderlistBean {
            /**
             * id : b827ebac498d5b56ff70e1ad3-jncity
             * parksname : 凌波山庄西小马烧烤停车场
             * plate : 辽N69Z72
             * platecolor : blue
             * parkbegin : 1606867374
             * parkend : 1606906984
             * totalfee : 1200
             * discountfee : 0
             * paidfee : 0
             * refundfee : 0
             * settlestate : owed
             * invoiceno : null
             * contactphone :
             */

            private String id;
            private String parksname;
            private String plate;
            private String platecolor;
            private long parkbegin;
            private long parkend;
            private int totalfee;
            private int discountfee;
            private int paidfee;
            private int refundfee;
            private int unpaidfee;
            private String settlestate;//
            private String invoiceno;
            private String contactphone;

            private boolean isChecked;
            private String paystatus;

            public String getPaystatus() {
                return paystatus;
            }

            public void setPaystatus(String paystatus) {
                this.paystatus = paystatus;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
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

            public long getParkbegin() {
                return parkbegin;
            }

            public void setParkbegin(long parkbegin) {
                this.parkbegin = parkbegin;
            }

            public long getParkend() {
                return parkend;
            }

            public void setParkend(long parkend) {
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

            public String getInvoiceno() {
                return invoiceno;
            }

            public void setInvoiceno(String invoiceno) {
                this.invoiceno = invoiceno;
            }

            public String getContactphone() {
                return contactphone;
            }

            public void setContactphone(String contactphone) {
                this.contactphone = contactphone;
            }

            public int getUnpaidfee() {
                return unpaidfee;
            }

            public void setUnpaidfee(int unpaidfee) {
                this.unpaidfee = unpaidfee;
            }
        }
    }
}
