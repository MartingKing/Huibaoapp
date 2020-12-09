package com.huiboapp.mvp.model.entity;

/**
 * Null
 * 通用空实体类
 */
public class NullEntity {


    /**
     * ackmsg : accountrecharge
     * ackmsgid : 1000
     * result : 300000
     * reason : SUCCESS
     * data : {"out_trade_no":"2020120800138600010080563155","pay_info":{"order_info":"version=1.0&app_id=2019112269319679&format=json&charset=utf-8&sign_type=RSA2&timestamp=2020-12-08%2000%3A13%3A25&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fhuibo.parkingquickly.com%2Fapi%2Fpay%2Fapp%2Falipay%2Fapp%2Fnotify&biz_content=%7B%22out_trade_no%22%3A%222020120800138600010080563155%22%2C%22subject%22%3A%22%E5%81%9C%E8%BD%A6%E8%B4%B9%22%2C%22total_amount%22%3A%220.50%22%2C%22timeout_express%22%3A%2210m%22%2C%22passback_params%22%3A%22MP20201107166DABA951%22%2C%22extend_params%22%3A%7B%22sys_service_provider_id%22%3A%222088031405001891%22%7D%7D&sign=mG2UEvV%2Bx3dG2ARZF%2BcScx%2ByZ5YzgN%2FNXP4oqyeFZ7grZnZu6JUMihkvC1%2BVfKWmq1mOXO%2FGO6RJCw0DTCNcrEMX7cYaWFGZmOuveY%2BOQC8SkOUY8zqLlIPk8dlT8no2ZFDyYFJg86e9cp%2Bbuo3xtQkY6ybhA3iUpAA545V7EvYTYTYNM4%2BB3HRXwcBuYXwJeIhKVspyzckOhRB21HD97C3jIaqrf1Fl%2B%2B0OjX3Vn2z26k1t%2BNHDfL7Cp5IVMZ858YisOZ4i8Xitrv%2B3KOYYkqOpliAavL4v1DRTIxy4tF5A6cHQheT%2Fg1F31sFnoockSWn%2FMtYRNyRYFhTxiqm5IQ%3D%3D"}}
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
         * out_trade_no : 2020120800138600010080563155
         * pay_info : {"order_info":"version=1.0&app_id=2019112269319679&format=json&charset=utf-8&sign_type=RSA2&timestamp=2020-12-08%2000%3A13%3A25&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fhuibo.parkingquickly.com%2Fapi%2Fpay%2Fapp%2Falipay%2Fapp%2Fnotify&biz_content=%7B%22out_trade_no%22%3A%222020120800138600010080563155%22%2C%22subject%22%3A%22%E5%81%9C%E8%BD%A6%E8%B4%B9%22%2C%22total_amount%22%3A%220.50%22%2C%22timeout_express%22%3A%2210m%22%2C%22passback_params%22%3A%22MP20201107166DABA951%22%2C%22extend_params%22%3A%7B%22sys_service_provider_id%22%3A%222088031405001891%22%7D%7D&sign=mG2UEvV%2Bx3dG2ARZF%2BcScx%2ByZ5YzgN%2FNXP4oqyeFZ7grZnZu6JUMihkvC1%2BVfKWmq1mOXO%2FGO6RJCw0DTCNcrEMX7cYaWFGZmOuveY%2BOQC8SkOUY8zqLlIPk8dlT8no2ZFDyYFJg86e9cp%2Bbuo3xtQkY6ybhA3iUpAA545V7EvYTYTYNM4%2BB3HRXwcBuYXwJeIhKVspyzckOhRB21HD97C3jIaqrf1Fl%2B%2B0OjX3Vn2z26k1t%2BNHDfL7Cp5IVMZ858YisOZ4i8Xitrv%2B3KOYYkqOpliAavL4v1DRTIxy4tF5A6cHQheT%2Fg1F31sFnoockSWn%2FMtYRNyRYFhTxiqm5IQ%3D%3D"}
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
             * order_info : version=1.0&app_id=2019112269319679&format=json&charset=utf-8&sign_type=RSA2&timestamp=2020-12-08%2000%3A13%3A25&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fhuibo.parkingquickly.com%2Fapi%2Fpay%2Fapp%2Falipay%2Fapp%2Fnotify&biz_content=%7B%22out_trade_no%22%3A%222020120800138600010080563155%22%2C%22subject%22%3A%22%E5%81%9C%E8%BD%A6%E8%B4%B9%22%2C%22total_amount%22%3A%220.50%22%2C%22timeout_express%22%3A%2210m%22%2C%22passback_params%22%3A%22MP20201107166DABA951%22%2C%22extend_params%22%3A%7B%22sys_service_provider_id%22%3A%222088031405001891%22%7D%7D&sign=mG2UEvV%2Bx3dG2ARZF%2BcScx%2ByZ5YzgN%2FNXP4oqyeFZ7grZnZu6JUMihkvC1%2BVfKWmq1mOXO%2FGO6RJCw0DTCNcrEMX7cYaWFGZmOuveY%2BOQC8SkOUY8zqLlIPk8dlT8no2ZFDyYFJg86e9cp%2Bbuo3xtQkY6ybhA3iUpAA545V7EvYTYTYNM4%2BB3HRXwcBuYXwJeIhKVspyzckOhRB21HD97C3jIaqrf1Fl%2B%2B0OjX3Vn2z26k1t%2BNHDfL7Cp5IVMZ858YisOZ4i8Xitrv%2B3KOYYkqOpliAavL4v1DRTIxy4tF5A6cHQheT%2Fg1F31sFnoockSWn%2FMtYRNyRYFhTxiqm5IQ%3D%3D
             */

            private String order_info;

            public String getOrder_info() {
                return order_info;
            }

            public void setOrder_info(String order_info) {
                this.order_info = order_info;
            }
        }
    }
}
