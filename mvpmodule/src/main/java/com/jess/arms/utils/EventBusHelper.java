package com.jess.arms.utils;


import org.greenrobot.eventbus.EventBus;

/**
 * EventBus的帮助类，主要是避免因为重复注册导致的崩溃问题
 * Created by Administrator on 2016/5/20.
 */
public class EventBusHelper {
    /**
     * 最好放在所有view都初始化之后，防止postStickyEvent报空指针异常
     * @param obj
     */
    public static void register(Object obj){
        //避免因为重复注册导致的崩溃问题
        if(!EventBus.getDefault().isRegistered(obj)){
            EventBus.getDefault().register(obj);
        }
    }

    public static void postStickyEvent(Object object){
        EventBus.getDefault().postSticky(object);
    }

    public static void removeStickyEvent(Object object){
        EventBus.getDefault().removeStickyEvent(object);
    }

    public static void removeStickyEvent(Class cls){
        EventBus.getDefault().removeStickyEvent(cls);
    }

    public static void unRegister(Object obj){
        EventBus.getDefault().unregister(obj);
    }

    public static void postEvent(Object object){
        EventBus.getDefault().post(object);
    }
}
