package com.mmb;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.commonlib.agentweb.AgentWeb;
import com.megvii.meglive_sdk.listener.DetectCallback;
import com.megvii.meglive_sdk.listener.PreCallback;
import com.megvii.meglive_sdk.manager.MegLiveManager;
import com.mmb.activity.IDCardDetectActivity;
import com.mmb.bridge.JsBridgeForMain;
import com.mmb.common.BaseWebActivity;
import com.mmb.contract.BaseContract;
import com.mmb.interfaces.OcrPermissionAuthListener;
import com.mmb.permission.PermissionListener;
import com.mmb.permission.PermissionUtil;
import com.mmb.utils.AuthrizationDialogUtil;
import com.mmb.utils.CommonUtils;
import com.mmb.utils.DialogUtil;
import com.mmb.utils.HProgressDialogUtils;
import com.mmb.utils.OcrUtils;
import com.mmb.utils.PhotoUtils;
import com.mmb.utils.RootAuthorizationUtil;
import com.mmb.utils.ToastUtils;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate._XUpdate;
import com.xuexiang.xupdate.service.OnFileDownloadListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Route(path = "/ddj/main")
public class DdjMainActivity extends BaseWebActivity implements BaseContract.MainView, DetectCallback, PreCallback {
    private static final String TAG = "MainActivity";
    @Autowired
    public String baseUrl;
    //获取相机权限 跳转身份证识别界面
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
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
    public String gpsStatus = "0";
    private String mBizToken;
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
//        JsBridgeForMain jsBridgeForMain = new JsBridgeForMain(new SoftReference<BaseContract.BaseView>(this),new SoftReference<WebView>(mAgentWeb.getWebCreator().getWebView()));
//        mAgentWeb.getJsInterfaceHolder().addJavaObject("android",jsBridgeForMain);
//        jsBridgeForMain.addDdjWidget(new TitleWidget());

        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new JsBridgeForMain(new JsBridgeForMain.JsMessageHandler() {
            @Override
            public void onOcrClicked() {
                requestPermission(permissions);
                if (chekCameraReadWriteStoragePerm()) {
                    new OcrUtils(DdjMainActivity.this, new OcrPermissionAuthListener() {
                        @Override
                        public void onPermissionAuthed() {
                            Intent intent = new Intent(DdjMainActivity.this, IDCardDetectActivity.class);
                            startActivityForResult(intent, CAMERA_PERMISSION_REQUEST_CODE);
                        }
                    }).setOCRConfig(mOcrType);
                }
            }

            @Override
            public void onMegliveClicked(String bizToken) {
                mBizToken = bizToken;
                requestPermission(permissions);
                if (chekCameraReadWriteStoragePerm()) {
                    beginDetect();
                }
            }

            @Override
            public void onPersionWithIdcardPicCalled() {
                requestPermission(permissions);
                new DialogUtil(DdjMainActivity.this, false, R.style.dialog, new DialogUtil.OnCloseListener() {
                    @Override
                    public void onGalleryClicked() {
                        if (chekCameraReadWriteStoragePerm()) {
                            PhotoUtils.openPic(DdjMainActivity.this, REQUEST_CODE_PIC_PHOTO);
                        }
                    }

                    @Override
                    public void onCameraClicked() {
                        if (chekCameraReadWriteStoragePerm()) {
                            imageUri = Uri.fromFile(fileUri);
                            //通过FileProvider创建一个content类型的Uri
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                imageUri = FileProvider.getUriForFile(DdjMainActivity.this, "com.financial.ddj.fileprovider", fileUri);
                            }
                            PhotoUtils.takePicture(DdjMainActivity.this, imageUri, REQUEST_CODE_TAKE_PHOTO);
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

            @Override
            public void isBackPressed() {
                DdjMainActivity.this.finish();
            }

        }));
    }

    private void requestPermission(String[] per) {
        permissionUtil = new PermissionUtil(DdjMainActivity.this);
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
            new AuthrizationDialogUtil(DdjMainActivity.this, content, true, R.style.dialog, new AuthrizationDialogUtil.OnCloseListener() {
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
        XUpdate.newBuild(DdjMainActivity.this)
                .build()
                .download(url, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(DdjMainActivity.this, "下载进度", false);
                    }

                    @Override
                    public void onProgress(float progress, long total) {
                        HProgressDialogUtils.setProgress(Math.round(progress * 100));
                    }

                    @Override
                    public boolean onCompleted(File file) {
                        HProgressDialogUtils.cancel();
                        // 安装apk
                        _XUpdate.startInstallApk(DdjMainActivity.this, file);
                        return false;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        HProgressDialogUtils.cancel();
                        ToastUtils.showShort(DdjMainActivity.this, "App下载失败,请切换网络或稍后重试。");
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
    private MyLocationListener myListener = new MyLocationListener();

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
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            province = location.getProvince();    //获取省份
            city = location.getCity();    //获取城市
            district = location.getDistrict();    //获取区县
            street = location.getStreet();    //获取街道信息
            if (TextUtils.isEmpty(province)) {
                ToastUtils.showShort(DdjMainActivity.this, "获取不到位置信息，请打开手机定位功能！");
            }
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

    private MegLiveManager megLiveManager;

    private void beginDetect() {
        megLiveManager = MegLiveManager.getInstance();
        megLiveManager.preDetect(DdjMainActivity.this, mBizToken, null, "https://api.megvii.com", DdjMainActivity.this);
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
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                //身份证头像
                byte[] headImg = data.getByteArrayExtra("portraitimg_bitmap");
                //身份证正反面图片
                byte[] iDCardImg = data.getByteArrayExtra("idcardimg_bitmap");
                Bitmap bmpIDCardImg = BitmapFactory.decodeByteArray(iDCardImg, 0, iDCardImg.length);
                Map<String, String> map = new ArrayMap<>();
                map.put("msgId", mMsgId);
                map.put("type", mOcrType);
                String picFront = "";
                String picBack = "";
                if ("0".equals(mOcrType)) {
                    picFront = CommonUtils.bitmapToBase64(bmpIDCardImg);
                }
                if ("1".equals(mOcrType)) {
                    picBack = CommonUtils.bitmapToBase64(bmpIDCardImg);
                }
                map.put("frontFullImg", picFront);
                map.put("backFullImg", picBack);
                Log.e(TAG, "picFront: " + picFront);
                Log.e(TAG, "picBack: " + picBack);
                String ocrData = JSON.toJSONString(map);
                Log.e(TAG, "onActivityResult: " + ocrData);
                mAgentWeb.getJsAccessEntrace().quickCallJs("webViewBridge.callback_('" + ocrData + "')");
                break;
            case REQUEST_CODE_PIC_PHOTO://手机相册
                cropImageUri = Uri.fromFile(fileCropUri);
                Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    newUri = FileProvider.getUriForFile(this, "com.jryj.fileprovider", new File(Objects.requireNonNull(newUri.getPath())));
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


    /**
     * @param token        业务流水号，用户调用预处理传入的token
     * @param errorCode    预处理结果码,具体错误码参考文档ErrorCode 说明
     * @param errorMessage 预处理结果信息，具体错误信息参考文档ErrorCode说明
     * @param data         客户端完成验证后的加密数据。注：需用该data调用服务端接口进行数据验证
     */
    @Override
    public void onDetectFinish(String token, int errorCode, String errorMessage, String data) {
        Map<String, Object> meglivMap = new ArrayMap<>();
        if (errorCode == 1000) {
            meglivMap.clear();
            meglivMap.put("msgId", mMsgId);
            meglivMap.put("megliveData", Base64.encodeToString(data.getBytes(), Base64.DEFAULT | Base64.NO_WRAP | Base64.NO_PADDING));
            String megliveData = JSON.toJSONString(meglivMap);
            Log.e(TAG, "JSON: " + megliveData);
            mAgentWeb.getJsAccessEntrace().quickCallJs("webViewBridge.callback_('" + megliveData + "')");
        }
        if (errorCode == 6000) {
            meglivMap.clear();
            meglivMap.put("msgId", mMsgId);
            meglivMap.put("status", "cancel");
            meglivMap.put("megliveData", "");
            String megliveData = JSON.toJSONString(meglivMap);
            mAgentWeb.getJsAccessEntrace().quickCallJs("webViewBridge.callback_('" + megliveData + "')");
        }
    }

    /**
     * 开启预处理
     * 此处无实际意义，代表预处理开启，此处可用于展示progress bar
     */
    @Override
    public void onPreStart() {

    }

    /**
     * 完成预处理之后的回调
     * 只有当完成预处理且errorCode==1000的时候才可以调用startDetect开启活体检测
     *
     * @param token        业务流水号，用户调用预处理传入的token
     * @param errorCode    预处理结果码,具体错误码参考文档ErrorCode 说明
     * @param errorMessage 预处理结果信息，具体错误信息参考文档ErrorCode说明
     */
    @Override
    public void onPreFinish(String token, int errorCode, String errorMessage) {
        if (errorCode == 1000) {
            megLiveManager.setVerticalDetectionType(MegLiveManager.DETECT_VERITICAL_FRONT);
            //开始活体认证
            megLiveManager.startDetect(this);
        }
    }

}
