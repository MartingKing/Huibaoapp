package com.mmb.entity;

public class JsDataEntity {

    private String msgId;
    private EventBean event;
    private DataBean data;

    @Override
    public String toString() {
        return "JsDataEntity{" +
                "msgId='" + msgId + '\'' +
                ", event=" + event +
                ", data=" + data +
                '}';
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public EventBean getEvent() {
        return event;
    }

    public void setEvent(EventBean event) {
        this.event = event;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class EventBean {

        private String type;
        private String operation;

        @Override
        public String toString() {
            return "EventBean{" +
                    "type='" + type + '\'' +
                    ", operation='" + operation + '\'' +
                    '}';
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }
    }

    public static class DataBean {
        /**
         * ocrType : 0
         */

        private String ocrType;
        private String url;
        private String bizTokenStr;
        private String orderNo;
        private String openApiAppId;
        private String openApiAppVersion;
        private String openApiNonce;
        private String openApiUserId;
        private String openApiSign;

        @Override
        public String toString() {
            return "DataBean{" +
                    "ocrType='" + ocrType + '\'' +
                    ", url='" + url + '\'' +
                    ", bizTokenStr='" + bizTokenStr + '\'' +
                    ", orderNo='" + orderNo + '\'' +
                    ", openApiAppId='" + openApiAppId + '\'' +
                    ", openApiAppVersion='" + openApiAppVersion + '\'' +
                    ", openApiNonce='" + openApiNonce + '\'' +
                    ", openApiUserId='" + openApiUserId + '\'' +
                    ", openApiSign='" + openApiSign + '\'' +
                    '}';
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOpenApiAppId() {
            return openApiAppId;
        }

        public void setOpenApiAppId(String openApiAppId) {
            this.openApiAppId = openApiAppId;
        }

        public String getOpenApiAppVersion() {
            return openApiAppVersion;
        }

        public void setOpenApiAppVersion(String openApiAppVersion) {
            this.openApiAppVersion = openApiAppVersion;
        }

        public String getOpenApiNonce() {
            return openApiNonce;
        }

        public void setOpenApiNonce(String openApiNonce) {
            this.openApiNonce = openApiNonce;
        }

        public String getOpenApiUserId() {
            return openApiUserId;
        }

        public void setOpenApiUserId(String openApiUserId) {
            this.openApiUserId = openApiUserId;
        }

        public String getOpenApiSign() {
            return openApiSign;
        }

        public void setOpenApiSign(String openApiSign) {
            this.openApiSign = openApiSign;
        }

        public String getBizTokenStr() {
            return bizTokenStr;
        }

        public void setBizTokenStr(String bizTokenStr) {
            this.bizTokenStr = bizTokenStr;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getOcrType() {
            return ocrType;
        }

        public void setOcrType(String ocrType) {
            this.ocrType = ocrType;
        }
    }
}
