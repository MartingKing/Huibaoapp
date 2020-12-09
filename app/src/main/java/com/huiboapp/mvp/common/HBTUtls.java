package com.huiboapp.mvp.common;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.commonlib.agentweb.utils.AppUtils;
import com.huiboapp.app.utils.RegexUtils;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.jess.arms.utils.ArmsUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HBTUtls {

    public static boolean checkInput(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            ArmsUtils.makeText(AppUtils.getApp(), "请输入手机号！");
            return false;
        }
        if (!RegexUtils.isMobileSimple(mobile)) {
            ArmsUtils.makeText(AppUtils.getApp(), "请输入正确的手机号！");
            return false;
        }
        return true;
    }

    public static String getKm(int distance) {
        String s;
        if (distance >= 1000) {
            int m = distance % 1000;
            int k = distance / 1000;
            String mm = String.valueOf(m).length() > 2 ? String.valueOf(m).substring(0, 2) : String.valueOf(m);
            s = k + "." + mm + "km";
        } else {
            s = distance + "m";
        }
        return s;
    }

    public static final String msg1 = "getregistedvertificationcode";
    public static final String msg2 = "updatememberpassword";
    public static final String msg3 = "getvertificationcode";
    public static final String regist = "registration";
    public static final String pwdlogin = "login";
    public static final String splash = "slash";
    public static final String mainpageslide = "mainpageslide";
    public static final String bindcar = "bindplate";
    public static final String unbindcar = "unbindplate";
    public static final String memberinfo = "getmemberinfo";
    public static final String autopay = "autopay";
    public static final String queryorderlist = "queryorderlist";
    public static final String queryorderdetail = "queryorderdetail";
    public static final String queryparkslist = "queryparkslist";
    public static final String payorder = "payparkingorder";
    public static final String supplementary = "supplementary";
    public static final String accountrecharge = "accountrecharge";
    public static final String bindphone = "bindphone";
    public static final String querylatestorder = "querylatestorder";
    public static final String parkinfo = "parkinfo";
    public static final String reserveparking = "reserveparking";

    public static Map<String, String> getParams(String msg) {
        Map<String, String> params = new ArrayMap<>();
        params.put("msg", msg);
        params.put("channelid", "1");
        params.put("msgid", "1000");
        params.put("sign", "12345");
        return params;
    }

    public static Map<String, Object> getParamsObject(String msg) {
        Map<String, Object> params = new ArrayMap<>();
        params.put("msg", msg);
        params.put("channelid", "1");
        if (!TextUtils.isEmpty(UserInfoHelper.getInstance().getToken())) {
            params.put("token", UserInfoHelper.getInstance().getToken());
        }
        params.put("msgid", "1000");
        params.put("sign", "12345");
        return params;
    }


    public static List<String> getCity() {
        String[] carType = {"京", "津", "冀", "晋","蒙","辽","吉","黑","沪","苏","浙","皖","闽",
                "赣","鲁","豫","鄂","湘","粤","桂","琼","渝","川","黔","滇","藏","陕","甘","青","宁","新","台","港","澳"};
        List<String> data = new ArrayList<>();
        for (int i = 0; i < carType.length; i++) {
            data.add(i, carType[i]);
        }
        return data;
    }

    public static List<String> getUseType() {
        String[] carType = {"营运机动车", "非营运机动车"};
        List<String> data = new ArrayList<>();
        for (int i = 0; i < carType.length; i++) {
            data.add(i, carType[i]);
        }
        return data;
    }

    public static Bitmap getBitmapFormUri(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = AppUtils.getApp().getContentResolver().openInputStream(uri);

        //这一段代码是不加载文件到内存中也得到bitmap的真是宽高，主要是设置inJustDecodeBounds为true
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;//不加载到内存
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;

        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        input = AppUtils.getApp().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }


    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            if (options <= 0)
                break;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 横屏可通过 widthPixels - widthPixels2 > 0 来判断底部导航栏是否存在
     *
     * @param windowManager
     * @return true表示有虚拟导航栏 false没有虚拟导航栏
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isNavigationBarShow(WindowManager windowManager) {
        Display defaultDisplay = windowManager.getDefaultDisplay();
        //获取屏幕高度
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        //宽度
        int widthPixels = outMetrics.widthPixels;


        //获取内容高度
        DisplayMetrics outMetrics2 = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics2);
        int heightPixels2 = outMetrics2.heightPixels;
        //宽度
        int widthPixels2 = outMetrics2.widthPixels;

        return heightPixels - heightPixels2 > 0 || widthPixels - widthPixels2 > 0;
    }


    public static void setPopupWindowTouchModal(PopupWindow popupWindow, boolean touchModal) {
        if (null == popupWindow) {
            return;
        }
        Method method;
        try {
            method = PopupWindow.class.getDeclaredMethod("setTouchModal", boolean.class);
            method.setAccessible(true);
            method.invoke(popupWindow, touchModal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPayModel(int m) {
        String model = "";
        switch (m) {
            case 1:
                model = "ewallet";
                break;
            case 2:
                model = "wxapp";
                break;
            case 3:
                model = "aliapp";
                break;
            default:
                model = "ewallet";
                break;
        }
        return model;
    }

}