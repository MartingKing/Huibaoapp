package com.huiboapp.mvp.ui.activity;

import android.Manifest;
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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.PhotoUtils;
import com.huiboapp.app.utils.ToastUtils;
import com.huiboapp.di.component.DaggerUserDetailComponent;
import com.huiboapp.di.module.UserDetailModule;
import com.huiboapp.mvp.contract.UserDetailContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.model.entity.CommonEntity;
import com.huiboapp.mvp.presenter.UserDetailPresenter;
import com.huiboapp.mvp.ui.widget.views.ChoosePicPopWindow;
import com.jess.arms.di.component.AppComponent;

import java.io.File;

import butterknife.BindView;

public class UserDetailActivity extends MBaseActivity<UserDetailPresenter> implements UserDetailContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.rignticon)
    ImageView rignticon;
    @BindView(R.id.header)
    ImageView header;
    @BindView(R.id.rlheader)
    RelativeLayout rlheader;
    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.tvphpne)
    EditText tvphpne;
    @BindView(R.id.verifyid)
    Button verifyid;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.container)
    LinearLayout container;
    private Uri imageUri;
    private Uri cropImageUri;
    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserDetailComponent
                .builder()
                .appComponent(appComponent)
                .userDetailModule(new UserDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_usedetail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
//        initAdapter();
        tvTitle.setText("个人信息");
        ivBack.setOnClickListener(this);
        rlheader.setOnClickListener(this);
        verifyid.setOnClickListener(this);
        save.setOnClickListener(this);
        if (UserInfoHelper.getInstance().getUserInfo()!=null){
            nickname.setText(UserInfoHelper.getInstance().getUserInfo().getNickname());
            tvphpne.setText(UserInfoHelper.getInstance().getUserInfo().getMsisdn());
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlheader:
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
            case R.id.verifyid:
                startActivity(new Intent(UserDetailActivity.this, VerifyIdActivity.class));
                break;
            case R.id.save:
                mPresenter.getMemberInfo();
                break;

        }
    }

    @Override
    public void starPay(CommonEntity UserDetailEntity) {

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
                    header.setImageBitmap(bitmap);
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

}
