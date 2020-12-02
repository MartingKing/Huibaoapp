package com.mmb.tencent_ocr;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.commonlib.agentweb.AgentWeb;
import com.mmb.R;
import com.mmb.bridge.JsBridgeForTecentApi;
import com.mmb.common.BaseWebActivity;
import com.mmb.contract.BaseContract;
import com.mmb.entity.JsDataEntity;
import com.mmb.permission.PermissionListener;
import com.mmb.permission.PermissionUtil;
import com.mmb.utils.AppManager;
import com.mmb.utils.AuthrizationDialogUtil;
import com.mmb.utils.BackpressDialogUtil;
import com.mmb.utils.CommonUtils;
import com.mmb.utils.DialogUtil;
import com.mmb.utils.HProgressDialogUtils;
import com.mmb.utils.PhotoUtils;
import com.mmb.utils.RootAuthorizationUtil;
import com.mmb.utils.ToastUtils;
import com.webank.mbank.ocr.WbCloudOcrSDK;
import com.webank.mbank.ocr.net.EXIDCardResult;
import com.webank.mbank.ocr.tools.ErrorCode;
import com.webank.normal.tools.WLogger;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate._XUpdate;
import com.xuexiang.xupdate.service.OnFileDownloadListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 腾讯OCR以及人脸识别
 */
@Route(path = "/ddj/tencent_ocr")
public class TecentIdentityReconitionActivity extends BaseWebActivity implements BaseContract.MainView {
    private static final String TAG = "TecentIdentity";
    @Autowired
    public String baseUrl;

    public static final int CAMERA_CODE = 300;
    //选择相册图片
    private static final int REQUEST_CODE_PIC_PHOTO = 103;
    //拍照
    private static final int REQUEST_CODE_TAKE_PHOTO = 104;
    //相册和相机所得图片
    private static final int CODE_RESULT_REQUEST = 107;
    //联系人
    private static final int READ_REQUEST_CONTACTS = 109;

    private LinearLayout mLayout;
    public static String mMsgId = "-1";
    public static String mOcrType = "0";
    public static boolean isNeedIntercepeBackgress = false;
    public String gpsStatus = "0";
    private Uri imageUri;
    private Uri cropImageUri;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");

    //***************************权限管理*******************************
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String[] permissions1 = new String[]{Manifest.permission.READ_CONTACTS};
    String[] permissions2 = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

    private PermissionUtil permissionUtil;
    private List<String> mDeniedPermission = new ArrayList<>();
    private List<String> mForbidenPermission = new ArrayList<>();

    @Override
    protected int getRootLayoutViewResId() {

        return R.layout.activity_main;
    }

    @Override
    protected void onCreateFinal(@Nullable Bundle savedInstanceState) {
        mLayout = findViewById(R.id.ll_root);

    }

    @Override
    protected void onCreatAgentWebFinal(AgentWeb mAgentWeb) {

        WBH5FaceVerifySDK.getInstance().setWebViewSettings(mAgentWeb.getWebCreator().getWebView(), getApplicationContext());
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new JsBridgeForTecentApi(new JsBridgeForTecentApi.JsMessageHandler() {
            @Override
            public void onOcrClicked(JsDataEntity.DataBean mDatas) {
                startTecentOcr(mDatas);
            }

            @Override
            public void onPersionWithIdcardPicCalled() {
                requestPermission(permissions);
                new DialogUtil(TecentIdentityReconitionActivity.this, false, R.style.dialog, new DialogUtil.OnCloseListener() {
                    @Override
                    public void onGalleryClicked() {
                        if (chekCameraReadWriteStoragePerm()) {
                            PhotoUtils.openPic(TecentIdentityReconitionActivity.this, REQUEST_CODE_PIC_PHOTO);
                        }
                    }

                    @Override
                    public void onCameraClicked() {
                        if (chekCameraReadWriteStoragePerm()) {
                            imageUri = Uri.fromFile(fileUri);
                            //通过FileProvider创建一个content类型的Uri
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                imageUri = FileProvider.getUriForFile(TecentIdentityReconitionActivity.this, "com.financial.ddj.fileprovider", fileUri);
                            }
                            PhotoUtils.takePicture(TecentIdentityReconitionActivity.this, imageUri, REQUEST_CODE_TAKE_PHOTO);
                        }
                    }
                }).show();
            }

            @Override
            public void onAddressCalled() {
                requestPermission(permissions1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkPermission("android.permission.READ_CONTACTS")) {
                        intentToContact();
                        //queryContactPhoneNumber();
                    }
                } else {
                    // 低于6.0的手机直接访问
                    intentToContact();
                }
            }

            @Override
            public void onGpsCalled() {
                requestPermission(permissions2);
                if (checkPermission("android.permission.ACCESS_FINE_LOCATION")) {
                    gpsStatus = "1";
                    startLocation();
                }
            }

            @Override
            public void onVersionUpdate(String url) {
                download(url);
            }

        }));
    }

    private void requestPermission(String[] per) {
        permissionUtil = new PermissionUtil(TecentIdentityReconitionActivity.this);
        permissionUtil.requestPermissions(per,
                new PermissionListener() {
                    @Override
                    public void onGranted() {
                        //所有权限都已经授权
                        //Toast.makeText(MainActivity.this, "所有权限都已授权", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        mDeniedPermission = deniedPermission;
                        //Toast.makeText(MainActivity.this, "拒绝了权限" + deniedPermission.get(0), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onShouldShowRationale(List<String> deniedPermission) {
                        //勾选不在提示的权限
                        mForbidenPermission = deniedPermission;
                    }
                });
    }

    //人脸识别，选择照片等操作都需要这三个权限
    private boolean chekCameraReadWriteStoragePerm() {
        return checkPermission("android.permission.WRITE_EXTERNAL_STORAGE")
                && checkPermission("android.permission.READ_EXTERNAL_STORAGE")
                && checkPermission("android.permission.CAMERA");
    }

    private boolean checkPermission(String tag) {
        String content = "未开启使用权，前往应用设置开启";
        switch (tag) {
            case "android.permission.ACCESS_FINE_LOCATION":
                gpsStatus = "0";
                content = "未开启使用权，前往应用设置开启【位置定位】权限，继续完成所在地址";
                break;
            case "android.permission.READ_CONTACTS":
                content = "未开启使用权，前往应用设置开启【通讯录读取】权限，继续完成添加联系人";
                break;
            case "android.permission.READ_EXTERNAL_STORAGE":
                content = "未开启使用权，前往应用设置开启【读写手机内存】权限，继续完成操作";
                break;
            case "android.permission.WRITE_EXTERNAL_STORAGE":
                content = "未开启使用权，前往应用设置开启【读写手机内存】权限，继续完成操作";
                break;
            case "android.permission.CAMERA":
                content = "未开启使用权，前往应用设置开启【相机】权限，继续完成操作";
                break;
        }
        if (mDeniedPermission.contains(tag) || mForbidenPermission.contains(tag)) {
            new AuthrizationDialogUtil(TecentIdentityReconitionActivity.this, content, true, R.style.dialog, new AuthrizationDialogUtil.OnCloseListener() {
                @Override
                public void goSetting() {
                    RootAuthorizationUtil.gotoMiuiPermission();
                }
            }).show();
            return false;
        }
        return true;
    }

    private void download(String url) {
        XUpdate.newBuild(TecentIdentityReconitionActivity.this)
                .build()
                .download(url, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(TecentIdentityReconitionActivity.this, "下载进度", false);
                    }

                    @Override
                    public void onProgress(float progress, long total) {
                        HProgressDialogUtils.setProgress(Math.round(progress * 100));
                    }

                    @Override
                    public boolean onCompleted(File file) {
                        HProgressDialogUtils.cancel();
                        // 安装apk
                        _XUpdate.startInstallApk(TecentIdentityReconitionActivity.this, file);
                        return false;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        HProgressDialogUtils.cancel();
                        ToastUtils.showShort(TecentIdentityReconitionActivity.this, "App下载失败,请切换网络或稍后重试。");
                    }
                });
    }


    // 跳转到联系人界面
    private void intentToContact() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, READ_REQUEST_CONTACTS);
    }

    /**
     * 获取所有联系人电话和姓名
     */
    private void queryContactPhoneNumber() {
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, null);
        Map<String, String> contacts = new ArrayMap<>();
        assert cursor != null;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            // 取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameFieldColumnIndex);
            String number = cursor.getString(numberFieldColumnIndex);
            contacts.put(name, number);
        }
        cursor.close();
        String jsonData = JSON.toJSONString(contacts);
        Log.e(TAG, "queryContactPhoneNumber: " + jsonData);
    }

    //开启定位
    public LocationClient mLocationClient = null;
    private TecentIdentityReconitionActivity.MyLocationListener myListener = new TecentIdentityReconitionActivity.MyLocationListener();

    private void startLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        sendGpsToJs();
    }

    private String province, city, district, street;

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            province = location.getProvince();    //获取省份
            city = location.getCity();    //获取城市
            district = location.getDistrict();    //获取区县
            street = location.getStreet();    //获取街道信息

        }
    }

    private void sendGpsToJs() {
        Map<String, String> gpsMap = new ArrayMap<>();
        gpsMap.clear();
        gpsMap.put("prov", province);
        gpsMap.put("msgId", mMsgId);
        gpsMap.put("city", city);
        gpsMap.put("zone", district);
        gpsMap.put("detail", street);
        gpsMap.put("gpsStatus", gpsStatus);
        String gpsData = JSON.toJSONString(gpsMap);
        mAgentWeb.getJsAccessEntrace().quickCallJs("webViewBridge.callback_('" + gpsData + "')");
        Log.e(TAG, "gpsData: " + gpsData);
    }

    @Override
    protected ViewGroup getWebParent() {
        return mLayout;
    }

    @Override
    protected String getUrl() {
        return baseUrl;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (WBH5FaceVerifySDK.getInstance().receiveH5FaceVerifyResult(requestCode, resultCode, data))
            return;
        switch (requestCode) {
            case REQUEST_CODE_PIC_PHOTO://手机相册
                cropImageUri = Uri.fromFile(fileCropUri);
                Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    newUri = FileProvider.getUriForFile(this, "com.financial.ddj.fileprovider", new File(Objects.requireNonNull(newUri.getPath())));
                }
                PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, 480, 480, CODE_RESULT_REQUEST);
                break;
            case REQUEST_CODE_TAKE_PHOTO://拍照
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, 480, 480, CODE_RESULT_REQUEST);
                break;
            case CODE_RESULT_REQUEST:
                Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                Log.e(TAG, "personAndIdImg: " + CommonUtils.bitmapToBase64(bitmap));
                if (bitmap != null) {
                    Map<String, String> personAndIdImgMap = new ArrayMap<>();
                    personAndIdImgMap.clear();
                    personAndIdImgMap.put("msgId", mMsgId);
                    personAndIdImgMap.put("type", mOcrType);
                    personAndIdImgMap.put("personAndIdImg", CommonUtils.bitmapToBase64(bitmap));
                    String personPic = JSON.toJSONString(personAndIdImgMap);
                    mAgentWeb.getJsAccessEntrace().quickCallJs("webViewBridge.callback_('" + personPic + "')");
                }
                break;
            case READ_REQUEST_CONTACTS:
                if (data != null) {
                    Uri uri = data.getData();
                    String phoneNum = null;
                    String contactName = null;
                    // 创建内容解析者
                    ContentResolver contentResolver = getContentResolver();
                    Cursor cursor = null;
                    if (uri != null) {
                        cursor = contentResolver.query(uri,
                                new String[]{"display_name", "data1"}, null, null, null);
                    }
                    assert cursor != null;
                    while (cursor.moveToNext()) {
                        contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    cursor.close();
                    //  把电话号码中的  -  符号 替换成空格
                    if (phoneNum != null) {
                        phoneNum = phoneNum.replaceAll("-", " ");
                        // 空格去掉  为什么不直接-替换成"" 因为测试的时候发现还是会有空格 只能这么处理
                        phoneNum = phoneNum.replaceAll(" ", "");
                    }
                    Map<String, String> contactMap = new ArrayMap<>();
                    contactMap.clear();
                    contactMap.put("phone", phoneNum);
                    contactMap.put("msgId", mMsgId);
                    contactMap.put("name", contactName);
                    String contactPersonData = JSON.toJSONString(contactMap);
                    mAgentWeb.getJsAccessEntrace().quickCallJs("webViewBridge.callback_('" + contactPersonData + "')");
                    Log.e(TAG, "contactMap: " + contactMap);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED && permissions.length > i) {
                            new AuthrizationDialogUtil(TecentIdentityReconitionActivity.this, "请开启相关权限后再试试", true, R.style.dialog, new AuthrizationDialogUtil.OnCloseListener() {
                                @Override
                                public void goSetting() {
                                    RootAuthorizationUtil.gotoMiuiPermission();
                                }
                            }).show();
                            break;
                        }
                    }
                    WBH5FaceVerifySDK.jumToVideoPage(TecentIdentityReconitionActivity.this);
                } else {
                    WBH5FaceVerifySDK.jumToVideoPage(TecentIdentityReconitionActivity.this);
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void startTecentOcr(final JsDataEntity.DataBean mDatas) {
        //启动SDK，进入SDK界面
        Bundle data = new Bundle();
        final WbCloudOcrSDK.WBOCRTYPEMODE type = mDatas.getOcrType().equals("0") ? WbCloudOcrSDK.WBOCRTYPEMODE.WBOCRSDKTypeFrontSide : WbCloudOcrSDK.WBOCRTYPEMODE.WBOCRSDKTypeBackSide;
        WbCloudOcrSDK.InputData inputData = new WbCloudOcrSDK.InputData(
                mDatas.getOrderNo(),
                mDatas.getOpenApiAppId(),
                mDatas.getOpenApiAppVersion(),
                mDatas.getOpenApiNonce(),
                mDatas.getOpenApiUserId(),
                mDatas.getOpenApiSign(),
                "ip=xxx.xxx.xxx.xxx",
                "lgt=xxx,xxx;lat=xxx.xxx");
        data.putSerializable(WbCloudOcrSDK.INPUT_DATA, inputData);
        data.putString(WbCloudOcrSDK.TITLE_BAR_COLOR, "#ffffff");
        data.putString(WbCloudOcrSDK.TITLE_BAR_CONTENT, "身份证识别");
        data.putString(WbCloudOcrSDK.WATER_MASK_TEXT, "仅供本次业务使用");
        data.putLong(WbCloudOcrSDK.SCAN_TIME, 20000);
        //    WLogger.d(TAG,"ocr flag content "+ocrFlagEt.getText().toString().trim());
        //启动SDK，进入SDK界面
        WbCloudOcrSDK.getInstance().init(TecentIdentityReconitionActivity.this, data, new WbCloudOcrSDK.OcrLoginListener() {
            @Override
            public void onLoginSuccess() {  //登录成功,拉起SDk页面
                WbCloudOcrSDK.getInstance().startActivityForOcr(TecentIdentityReconitionActivity.this, new WbCloudOcrSDK.IDCardScanResultListener() {  //证件结果回调接口
                    @Override
                    public void onFinish(String resultCode, String resultMsg) {
                        // resultCode为0，则刷脸成功；否则刷脸失败
                        if ("0".equals(resultCode)) {
                            WLogger.d(TAG, "识别成功");
                            // 登录成功  第三方应用对扫描的结果进行操作
                            EXIDCardResult result = WbCloudOcrSDK.getInstance().getResultReturn();
                            String orderNo = result.orderNo;
                            String sign = result.sign;
                            String frontFullImageSrc = result.frontFullImageSrc;
                            String backFullImageSrc = result.backFullImageSrc;
                            FileInputStream frontPic = null;
                            FileInputStream backPic = null;
                            String uri = getFilesDir().getPath();
                            try {
                                frontPic = new FileInputStream(CommonUtils.getDefaultValue(frontFullImageSrc, uri + "/ocr/No_1.jpg"));
                                backPic = new FileInputStream(CommonUtils.getDefaultValue(backFullImageSrc, uri + "/ocr/No_2.jpg"));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Bitmap bitmap1 = BitmapFactory.decodeStream(frontPic);
                            Bitmap bitmap2 = BitmapFactory.decodeStream(backPic);
                            String picFront = CommonUtils.bitmapToBase64(bitmap1);
                            String picBack = CommonUtils.bitmapToBase64(bitmap2);
                            Map<String, String> map = new HashMap<>();
                            map.put("msgId", mMsgId);
                            map.put("type", mOcrType);
                            map.put("frontFullImg", picFront);
                            map.put("backFullImg", picBack);
                            map.put("status", "android");
                            map.put("orderNo", orderNo);
                            map.put("sign", sign);
                            Log.e(TAG, "picFront: " + picFront);
                            Log.e(TAG, "picBack: " + picBack);
                            String ocrData = JSON.toJSONString(map);
                            mAgentWeb.getJsAccessEntrace().quickCallJs("webViewBridge.callback_('" + ocrData + "')");
                        } else {
                            WLogger.d(TAG, "识别失败");
                        }
                    }
                }, type);
            }

            @Override
            public void onLoginFailed(String errorCode, String errorMsg) {
                if (errorCode.equals(ErrorCode.IDOCR_LOGIN_PARAMETER_ERROR)) {
                    Toast.makeText(TecentIdentityReconitionActivity.this, "传入参数有误！" + errorMsg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TecentIdentityReconitionActivity.this, "登录OCR sdk失败！" + "errorCode= " + errorCode + " ;errorMsg=" + errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        if (isNeedIntercepeBackgress) {
            showExitDialog();
        } else {
            super.onBackPressed();
        }
    }

    private void showExitDialog() {
        new BackpressDialogUtil(TecentIdentityReconitionActivity.this, "还差一步完成申请，确认放弃？", false, R.style.dialog, new BackpressDialogUtil.OnCloseListener() {
            @Override
            public void goExit() {
                AppManager.getAppManager().appExit(TecentIdentityReconitionActivity.this);
            }
        }).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mAgentWeb.getWebCreator().getWebView().canGoBack()) {
                showExitDialog();
                return true;
            } else {
                finish();
                return true;
            }

        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
