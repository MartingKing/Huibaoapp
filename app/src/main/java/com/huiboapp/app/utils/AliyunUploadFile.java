package com.huiboapp.app.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.DeleteObjectRequest;
import com.alibaba.sdk.android.oss.model.DeleteObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

public class AliyunUploadFile {

    private static final String AccessKey = "LTAI4GAnWfhoMPRNg9zcGW76";
    private static final String Secret = "E3EpocDkJH6x6Rm4gEcOGSl2Zxs3Lq";
    private static final String Endpoint = "oss-cn-hangzhou.aliyuncs.com";
    private static final String Bucket = "huiboapp";
    private AliyunUploadView aliyunUploadView;
    private OSSCredentialProvider credentialProvider;
    private ClientConfiguration conf;
    private OSS oss;

    public AliyunUploadFile(AliyunUploadView aliyunUploadView) {
        this.aliyunUploadView = aliyunUploadView;
    }

    /**
     * @param context        上下文
     * @param objectName     文件名
     * @param uploadFilePath 文件路径
     */
    public void UploadFile(Context context, String SecurityToken, String objectName, String uploadFilePath) {

        credentialProvider = new OSSStsTokenCredentialProvider(AccessKey, Secret, SecurityToken);
        conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(context, Endpoint, credentialProvider, conf);

        // 构造上传请求。
        PutObjectRequest putObjectRequest = new PutObjectRequest(Bucket, objectName, uploadFilePath);

        // 异步上传时可以设置进度回调。
        putObjectRequest.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

            }
        });

        OSSAsyncTask ossAsyncTask = oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                aliyunUploadView.UploadSuccess(oss.presignPublicObjectURL(Bucket, objectName));
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                    Log.e("123", clientExcepion + "");
                    aliyunUploadView.Uploaddefeated("网络异常");
                }

                if (serviceException != null) {
                    // 服务异常。
                    Log.e("123", serviceException + "");
                    aliyunUploadView.Uploaddefeated("服务异常");
                }
            }
        });

        // ossAsyncTask.cancel(); // 可以取消任务
         ossAsyncTask.waitUntilFinished(); // 等待任务完成
    }

    public void DeleteFile(Context context, String AccessKeyId, String SecretKeyId, String SecurityToken
            , String endpoint, String bucketName, String objectName) {

        credentialProvider = new OSSStsTokenCredentialProvider(AccessKeyId, SecretKeyId, SecurityToken);
        conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(context, endpoint, credentialProvider, conf);

        // 创建删除请求。
        DeleteObjectRequest delete = new DeleteObjectRequest(bucketName, objectName);
        // 异步删除。
        OSSAsyncTask deleteTask = oss.asyncDeleteObject(delete, new OSSCompletedCallback<DeleteObjectRequest, DeleteObjectResult>() {
            @Override
            public void onSuccess(DeleteObjectRequest request, DeleteObjectResult result) {

            }

            @Override
            public void onFailure(DeleteObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();

                }
                if (serviceException != null) {
                    // 服务异常。

                }
            }
        });

        // deleteTask.cancel(); // 可以取消任务
        // deleteTask.waitUntilFinished(); // 等待任务完成
    }

    public interface AliyunUploadView {
        void UploadSuccess(String url);

        void Uploaddefeated(String error);
    }
}
