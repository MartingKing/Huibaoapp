package com.huiboapp.mvp.ui.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.PhotoUtils;
import com.huiboapp.app.utils.ToastUtils;
import com.huiboapp.di.component.DaggerAddCarComponent;
import com.huiboapp.di.module.AddCarModule;
import com.huiboapp.mvp.common.HBTUtls;
import com.huiboapp.mvp.contract.AddCarContract;
import com.huiboapp.mvp.presenter.AddCarPresenter;
import com.huiboapp.mvp.ui.widget.views.ChoosePicPopWindow;
import com.huiboapp.mvp.ui.widget.views.SelectColorWindow;
import com.jess.arms.di.component.AppComponent;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

public class AddCarActivity extends MBaseActivity<AddCarPresenter> implements AddCarContract.View {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;
    @BindView(R.id.tv_add_car)
    TextView tvAddCar;
    @BindView(R.id.tv_color)
    TextView tvColor;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.et5)
    EditText et5;
    @BindView(R.id.et6)
    EditText et6;
    @BindView(R.id.et7)
    EditText et7;
    @BindView(R.id.et8)
    EditText et8;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.tv_selecttime)
    TextView tvSelecttime;
    @BindView(R.id.tv_selecttype)
    TextView tvSelecttype;
    @BindView(R.id.tv_usetype)
    TextView tvUsetype;
    @BindView(R.id.tv_switch)
    Switch tvSwitch;
    @BindView(R.id.rl_car_img)
    RelativeLayout rlCarImg;
    @BindView(R.id.myimg)
    ImageView myimg;
    @BindView(R.id.rl_container)
    RelativeLayout container;
    Calendar calendar = Calendar.getInstance(Locale.CHINA);
    EditText[] mArray;
    @BindView(R.id.et_cartype)
    EditText etCartype;
    @BindView(R.id.et_idnum)
    EditText etIdnum;
    @BindView(R.id.et_fdjnum)
    EditText etFdjnum;
    @BindView(R.id.et_owner)
    EditText etOwner;
    private Uri imageUri;
    private Uri cropImageUri;
    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;
    private boolean autoPay = false;
    private String selectColor = "blue";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddCarComponent
                .builder()
                .appComponent(appComponent)
                .addCarModule(new AddCarModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_addcar;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isBarDarkFont = false;
        tvTitle.setText("添加车辆");
        mArray = new EditText[]{et1, et2, et3, et4, et5, et6, et7, et8};
        initEditText();
        initEvent();

    }


    private void initEvent() {
        ivBack.setOnClickListener(this);
        tvColor.setOnClickListener(this);
        tvSelecttime.setOnClickListener(this);
        tvSelecttype.setOnClickListener(this);
        tvUsetype.setOnClickListener(this);
        submit.setOnClickListener(this);
        rlCarImg.setOnClickListener(this);
        tvSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO: 2020/12/4  身份识别验证
                autoPay = isChecked;
            }
        });
    }

    private void initEditText() {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < mArray.length; i++) {
            int finalI = i;
            mArray[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(s) && finalI > 0) {
                        mArray[finalI - 1].requestFocus();
                        mArray[finalI - 1].setFocusable(true);
                        mArray[finalI - 1].setFocusableInTouchMode(true);
                        if (mArray[finalI - 1].getText().toString().trim().length() > 0) {
                            mArray[finalI - 1].setSelection(1);
                        } else {
                            mArray[finalI - 1].setSelection(0);
                        }
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    stringBuffer.append(s);
                    if (s.length() > 0 && finalI < 7) {
                        mArray[finalI + 1].requestFocus();
                        mArray[finalI + 1].setFocusable(true);
                        mArray[finalI + 1].setFocusableInTouchMode(true);
                        mArray[finalI + 1].setSelection(0);
                    }
                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_color:
                initSelectWindow(1);
                break;
            case R.id.tv_selecttime:
                showTimePicker();
                break;
            case R.id.tv_selecttype:

                initSelectWindow(2);
                break;
            case R.id.tv_usetype:

                initSelectWindow(3);
                break;
            case R.id.rl_car_img:
                ChoosePicPopWindow choosePicPopWindow = new ChoosePicPopWindow(this, new ChoosePicPopWindow.SelectPicListerner() {
                    @Override
                    public void takephoto() {
                        autoObtainCameraPermission();
                    }

                    @Override
                    public void selecpic() {
                        autoObtainStoragePermission();
                    }
                });
                choosePicPopWindow.showAtLocation(container, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.submit:
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < 8; i++) {
                    stringBuilder.append(mArray[i].getText().toString());
                }
                String plate = stringBuilder.toString();
                String brColor = tvColor.getText().toString();
                String idNum = etIdnum.getText().toString();
                String brandmodel = etCartype.getText().toString();
                String fdjNum = etFdjnum.getText().toString();
                String owner = etOwner.getText().toString();
                String time = tvSelecttime.getText().toString();
                String cartype = tvSelecttype.getText().toString();
                String usecharacter = tvUsetype.getText().toString();
                if (plate.length() < 7 || TextUtils.isEmpty(plate)) {
                    ToastUtils.showShort(this, "请输入正确车牌号!");
                    return;
                }
                if (brColor.equals("绿色") && plate.length() < 8) {
                    ToastUtils.showShort(this, "选择的车型和车牌号不符!");
                    return;
                }
                if (TextUtils.isEmpty(idNum)) {
                    ToastUtils.showShort(this, "请输入车架号!");
                    return;
                }
                if (TextUtils.isEmpty(owner)) {
                    ToastUtils.showShort(this, "请输入车辆所有人!");
                    return;
                }

                Map<String, Object> params = HBTUtls.getParamsObject(HBTUtls.bindcar);
                params.put("plate", getDefaultS(plate));
                params.put("platecolor", selectColor);
                params.put("autoPay", autoPay);
                params.put("license", getDefaultS(HBTUtls.bitmapToBase64(bitmap)));
                params.put("vin", getDefaultS(idNum));
                params.put("brandmodel", getDefaultS(brandmodel));
                params.put("engineno", getDefaultS(fdjNum));
                params.put("owner", getDefaultS(owner));
                params.put("registdate", time);
                params.put("cartype", getDefaultS(cartype));
                params.put("usecharacter", getDefaultS(usecharacter));
                Log.e(TAG, "onClick: " + params);
                mPresenter.addYourCar(params);
                break;
        }
    }

    public String getDefaultS(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    private Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //相机返回
            case PhotoUtils.CODE_CAMERA_REQUEST:
                cropImageUri = Uri.fromFile(PhotoUtils.fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, PhotoUtils.CODE_RESULT_REQUEST);
                break;
            //相册返回
            case PhotoUtils.CODE_GALLERY_REQUEST:

                if (PhotoUtils.hasSdcard()) {
                    cropImageUri = Uri.fromFile(PhotoUtils.fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(this, "com.huiboapp.fileprovider", new File(newUri.getPath()));
                    }
                    PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, PhotoUtils.CODE_RESULT_REQUEST);
                } else {
                    ToastUtils.showShort(this, "设备没有SD卡！");
                }
                break;
            //裁剪返回
            case PhotoUtils.CODE_RESULT_REQUEST:
                bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                if (bitmap != null) {
                    myimg.setImageBitmap(bitmap);
                }
                break;
            default:
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case PhotoUtils.CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (PhotoUtils.hasSdcard()) {
                        imageUri = Uri.fromFile(PhotoUtils.fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //通过FileProvider创建一个content类型的Uri
                            imageUri = FileProvider.getUriForFile(this, "com.huiboapp.fileprovider", PhotoUtils.fileUri);
                        }
                        PhotoUtils.takePicture(this, imageUri, PhotoUtils.CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                } else {
                    ToastUtils.showShort(this, "请允许打开相机！！");
                }
                break;
            }
            //调用系统相册申请Sdcard权限回调
            case PhotoUtils.STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, PhotoUtils.CODE_GALLERY_REQUEST);
                } else {
                    ToastUtils.showShort(this, "请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    private void showTimePicker() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvSelecttime.setText(year + "-" + (Integer.valueOf(month) + 1) + "-" + dayOfMonth);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MARCH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void initSelectWindow(int type) {
        View popView = getLayoutInflater().inflate(R.layout.layout_select_color, null);
        RadioButton rb1 = popView.findViewById(R.id.rb1);
        RadioButton rb2 = popView.findViewById(R.id.rb2);
        RadioButton rb3 = popView.findViewById(R.id.rb3);
        RadioGroup rgColor = popView.findViewById(R.id.rgColor);
        RadioButton yy = popView.findViewById(R.id.yy);
        RadioButton fyy = popView.findViewById(R.id.fyy);
        RadioGroup rgUseType = popView.findViewById(R.id.rgUseType);
        RadioButton cx1 = popView.findViewById(R.id.cx1);
        RadioButton cx2 = popView.findViewById(R.id.cx2);
        RadioButton cx3 = popView.findViewById(R.id.cx3);
        RadioButton cx4 = popView.findViewById(R.id.cx4);
        RadioGroup rgCarType = popView.findViewById(R.id.rgCarType);
        Button pSubmip = popView.findViewById(R.id.select);

        switch (type) {
            case 1:
                rgColor.setVisibility(View.VISIBLE);
                rgCarType.setVisibility(View.GONE);
                rgUseType.setVisibility(View.GONE);
                break;
            case 2:
                rgColor.setVisibility(View.GONE);
                rgCarType.setVisibility(View.VISIBLE);
                rgUseType.setVisibility(View.GONE);
                break;
            case 3:
                rgColor.setVisibility(View.GONE);
                rgCarType.setVisibility(View.GONE);
                rgUseType.setVisibility(View.VISIBLE);
                break;
        }
        SelectColorWindow selectColorWindow = new SelectColorWindow(this, popView);
        selectColorWindow.showAtLocation(container, Gravity.CENTER, 0, 0);
        pSubmip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb1.isChecked()) {
                    selectColor = "blue";
                    tvColor.setText("蓝牌");
                }
                if (rb2.isChecked()) {
                    tvColor.setText("绿牌");
                    selectColor = "green";
                }
                if (rb3.isChecked()) {
                    tvColor.setText("黄牌");
                    selectColor = "yellow";
                }

                if (cx1.isChecked()) {
                    tvSelecttype.setText("大型车");
                }
                if (cx2.isChecked()) {
                    tvSelecttype.setText("中型车");
                }
                if (cx3.isChecked()) {
                    tvSelecttype.setText("小型车");
                }
                if (cx4.isChecked()) {
                    tvSelecttype.setText("微型车");
                }
                if (yy.isChecked()) {
                    tvUsetype.setText("营运机动车");
                }
                if (fyy.isChecked()) {
                    tvUsetype.setText("非营运机动车");
                }
                selectColorWindow.dismiss();
            }
        });
    }


    /**
     * 动态申请sdcard读写权限
     */
    private void autoObtainStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PhotoUtils.STORAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                PhotoUtils.openPic(this, PhotoUtils.CODE_GALLERY_REQUEST);
            }
        } else {
            PhotoUtils.openPic(this, PhotoUtils.CODE_GALLERY_REQUEST);
        }
    }

    /**
     * 申请访问相机权限
     */
    private void autoObtainCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "您已经拒绝过一次", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PhotoUtils.CAMERA_PERMISSIONS_REQUEST_CODE);
            } else {//有权限直接调用系统相机拍照
                if (PhotoUtils.hasSdcard()) {
                    imageUri = Uri.fromFile(PhotoUtils.fileUri);
                    //通过FileProvider创建一个content类型的Uri
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUri = FileProvider.getUriForFile(this, "com.huiboapp.fileprovider", PhotoUtils.fileUri);
                    }
                    PhotoUtils.takePicture(this, imageUri, PhotoUtils.CODE_CAMERA_REQUEST);
                } else {
                    Toast.makeText(this, "设备没有SD卡", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onSuccess() {
        ToastUtils.showShort(this, "添加成功");
    }

    @Override
    public void onFailed(String reson) {
        ToastUtils.showShort(this, reson);
    }
}
