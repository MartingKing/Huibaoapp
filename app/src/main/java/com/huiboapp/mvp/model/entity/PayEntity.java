package com.huiboapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class PayEntity {


    /**
     * ackmsg : payparkingorder
     * ackmsgid : 1000
     * result : 300000
     * reason : SUCCESS
     * data : {"out_trade_no":"2020120700261900016406736905","pay_info":{"appid":"wx76248284db4effc8","partnerid":"1238588602","prepay_id":"wx071747474613526dbeaa6548f697e60000","package":"Sign=WXPay","timestamp":1607334467,"nonce_str":"dfcd138ba643e490","sign_type":"MD5","sign":"5CE1E08F9C96864474750A768DD70447"}}
     */

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
        /**
         * out_trade_no : 2020120700261900016406736905
         * pay_info : {"appid":"wx76248284db4effc8","partnerid":"1238588602","prepay_id":"wx071747474613526dbeaa6548f697e60000","package":"Sign=WXPay","timestamp":1607334467,"nonce_str":"dfcd138ba643e490","sign_type":"MD5","sign":"5CE1E08F9C96864474750A768DD70447"}
         */

        private String out_trade_no;
        private PayInfoBean pay_info;
        private String id;
        private String parksid;
        private String parksname;
        private String pid;
        private String accessidentity;
        private String plate;
        private String platecolor;
        private String parkbegin;
        private String parkend;
        private String totalfee;
        private String discountfee;
        private String paidfee;
        private String refundfee;
        private String unpaidfee;
        private String settlestate;
        private String orderstate;
        private String servicescore;
        private String environmentscore;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getAccessidentity() {
            return accessidentity;
        }

        public void setAccessidentity(String accessidentity) {
            this.accessidentity = accessidentity;
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

        public String getTotalfee() {
            return totalfee;
        }

        public void setTotalfee(String totalfee) {
            this.totalfee = totalfee;
        }

        public String getDiscountfee() {
            return discountfee;
        }

        public void setDiscountfee(String discountfee) {
            this.discountfee = discountfee;
        }

        public String getPaidfee() {
            return paidfee;
        }

        public void setPaidfee(String paidfee) {
            this.paidfee = paidfee;
        }

        public String getRefundfee() {
            return refundfee;
        }

        public void setRefundfee(String refundfee) {
            this.refundfee = refundfee;
        }

        public String getUnpaidfee() {
            return unpaidfee;
        }

        public void setUnpaidfee(String unpaidfee) {
            this.unpaidfee = unpaidfee;
        }

        public String getSettlestate() {
            return settlestate;
        }

        public void setSettlestate(String settlestate) {
            this.settlestate = settlestate;
        }

        public String getOrderstate() {
            return orderstate;
        }

        public void setOrderstate(String orderstate) {
            this.orderstate = orderstate;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public PayInfoBean getPay_info() {
            return pay_info;
        }

        public void setPay_info(PayInfoBean pay_info) {
            this.pay_info = pay_info;
        }

        public static class PayInfoBean {
            /**
             * appid : wx76248284db4effc8
             * partnerid : 1238588602
             * prepay_id : wx071747474613526dbeaa6548f697e60000
             * package : Sign=WXPay
             * timestamp : 1607334467
             * nonce_str : dfcd138ba643e490
             * sign_type : MD5
             * sign : 5CE1E08F9C96864474750A768DD70447
             */
            private String order_info;
            private String appid;
            private String partnerid;
            private String prepay_id;
            @SerializedName("package")
            private String packageX;
            private String timestamp;
            private String nonce_str;
            private String sign_type;
            private String sign;

            public String getOrder_info() {
                return order_info;
            }

            public void setOrder_info(String order_info) {
                this.order_info = order_info;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepay_id() {
                return prepay_id;
            }

            public void setPrepay_id(String prepay_id) {
                this.prepay_id = prepay_id;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getNonce_str() {
                return nonce_str;
            }

            public void setNonce_str(String nonce_str) {
                this.nonce_str = nonce_str;
            }

            public String getSign_type() {
                return sign_type;
            }

            public void setSign_type(String sign_type) {
                this.sign_type = sign_type;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
