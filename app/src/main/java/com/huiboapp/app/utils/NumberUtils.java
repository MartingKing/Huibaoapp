package com.huiboapp.app.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * Created by yaojian on 2019/2/1 10:19
 */
public class NumberUtils {

    public static double roundHalfUp(int scale, double d){
        BigDecimal b = new BigDecimal(d);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 数字按精度截取 余位补0
     */
    public static String roundDownStr(int scale, String d) {
        if (TextUtils.isEmpty(d)) d = "0";
        BigDecimal b = new BigDecimal(d);
        return b.setScale(scale, BigDecimal.ROUND_DOWN).toPlainString();
    }

    /**
     * 数字按精度截取 余位补0
     */
    public static String roundUpStr(int scale, String d) {
        if (TextUtils.isEmpty(d)) d = "0";
        BigDecimal b = new BigDecimal(d);
        return b.setScale(scale, BigDecimal.ROUND_UP).toPlainString();
    }

    /**
     * 字符串转为double
     */
    public static double stringToDouble(String number) {
        if (TextUtils.isEmpty(number))
            return 0;

        try {
            return Double.valueOf(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 字符串转为int
     */
    public static int stringToInt(String number) {
        if (TextUtils.isEmpty(number))
            return 0;

        try {
            return Integer.valueOf(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
