package com.huiboapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class ChargeEntity {

    /**
     * ackmsg : accountrecharge
     * ackmsgid : 1000
     * result : 300000
     * reason : SUCCESS
     * data : {"out_trade_no":"2020120800745700010064899523","pay_info":{"appid":"wx76248284db4effc8","partnerid":"1238588602","prepay_id":"wx0800104862057392845c1fdede839e0000","package":"Sign=WXPay","timestamp":1607357448,"nonce_str":"4477caa794c440a6","sign_type":"MD5","sign":"F72172C2CDD40E78B99CBC05649A05A1"}}
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
         * out_trade_no : 2020120800745700010064899523
         * pay_info : {"appid":"wx76248284db4effc8","partnerid":"1238588602","prepay_id":"wx0800104862057392845c1fdede839e0000","package":"Sign=WXPay","timestamp":1607357448,"nonce_str":"4477caa794c440a6","sign_type":"MD5","sign":"F72172C2CDD40E78B99CBC05649A05A1"}
         */

        private String out_trade_no;
        private PayInfoBean pay_info;

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
             * prepay_id : wx0800104862057392845c1fdede839e0000
             * package : Sign=WXPay
             * timestamp : 1607357448
             * nonce_str : 4477caa794c440a6
             * sign_type : MD5
             * sign : F72172C2CDD40E78B99CBC05649A05A1
             */
            private String order_info;
            private String appid;
            private String partnerid;
            private String prepay_id;
            @SerializedName("package")
            private String packageX;
            private int timestamp;
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

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
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
