package com.mmb.bean;

/**
 * Created by lwc on 2019/6/27.
 */
public class BaseJsBean {

    private EventBean event;

    private String msgId;

    public EventBean getEvent() {
        return event;
    }
//    public String getData() {
//        return data;
//    }

    public static class EventBean {
        private String type;
        private String operation;

        public String getType() {
            return type;
        }

        public String getOperation() {
            return operation;
        }
    }
}
