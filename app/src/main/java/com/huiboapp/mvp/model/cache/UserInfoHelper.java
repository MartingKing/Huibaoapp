package com.huiboapp.mvp.model.cache;

import android.text.TextUtils;

import com.huiboapp.mvp.model.entity.UserInfoEntity;
import com.huiboapp.mvp.model.sp.UserInfoSp;

import java.util.List;

import timber.log.Timber;

/**
 * 用户信息缓存
 * <p>
 * Created by yaojian on 2018/11/26 10:38
 */
public class UserInfoHelper {

    private UserInfoHelper() {
    }

    public static synchronized UserInfoHelper getInstance() {
        return UserInfoHolder.instance;
    }

    private static class UserInfoHolder {
        private static final UserInfoHelper instance = new UserInfoHelper();
    }

    private String userName;
    private String userPhone;

    private String cookie;
    private String s1;
    private String s2;
    private String token;
    private int balance;
    private long loginTime;
    private List<UserInfoEntity.CarList> platelist;

    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = UserInfoSp.getParam(UserInfoSp.TOKEN);
        }
        return token;
    }

    private void setToken(String token) {
        this.token = token;
        UserInfoSp.setParam(UserInfoSp.TOKEN, token);
    }

    public String getS2() {
        if (TextUtils.isEmpty(s2))
            s2 = UserInfoSp.getParam(UserInfoSp.SESSION2);
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
        UserInfoSp.setParam(UserInfoSp.SESSION2, s2);
    }

    public String getS1() {
        if (TextUtils.isEmpty(s1))
            s1 = UserInfoSp.getParam(UserInfoSp.SESSION1);
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
        UserInfoSp.setParam(UserInfoSp.SESSION1, s1);
    }

    public String getCookie() {
        if (TextUtils.isEmpty(cookie))
            cookie = UserInfoSp.getParam(UserInfoSp.COOKIE);
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
        UserInfoSp.setParam(UserInfoSp.COOKIE, cookie);
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
        UserInfoSp.setLongParam(UserInfoSp.LOGIN_TIME, loginTime);
    }

    public long getLoginTime() {
        if (TextUtils.isEmpty(cookie))
            loginTime = UserInfoSp.getLongParam(UserInfoSp.LOGIN_TIME);
        return loginTime;
    }


    public String getUserName() {
        if (TextUtils.isEmpty(userName))
            userName = UserInfoSp.getParam(UserInfoSp.USER_NAME);
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
        UserInfoSp.setParam(UserInfoSp.USER_NAME, userName);
    }

    public String getUserPhone() {
        if (TextUtils.isEmpty(userPhone))
            userPhone = UserInfoSp.getParam(UserInfoSp.USER_PHONE);
        return userPhone;
    }

    private void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        UserInfoSp.setParam(UserInfoSp.USER_PHONE, userPhone);
    }

    public long getBalance() {
        userPhone = UserInfoSp.getParam(UserInfoSp.USER_BALANCE);
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        UserInfoSp.setParam(UserInfoSp.USER_BALANCE, balance);
    }

    public List<UserInfoEntity.CarList> getPlatelist() {
        platelist = UserInfoSp.getCarListParam(UserInfoSp.CARLIST);
        return platelist;
    }

    public void setPlatelist(List<UserInfoEntity.CarList> platelist) {
        this.platelist = platelist;
        UserInfoSp.setCarListParam(UserInfoSp.CARLIST, platelist);
    }

    public void setLoginInfo(UserInfoEntity param) {
        setUserName(param.getLoginname());
        setUserPhone(param.getMsisdn());
        setToken(param.getToken());
        setBalance(param.getBalance());
        setPlatelist(param.getPlatelist());
    }

    public void setUserInfo(UserInfoEntity param) {
        setUserName(param.getNickname());
        setUserPhone(param.getMsisdn());
        setBalance(param.getBalance());
    }

    public void clearUserInfo() {
        Timber.e("清理数据");
        setUserName("");
        setUserPhone("");
        setToken("");
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }
}
