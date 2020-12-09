package com.huiboapp.app.utils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.commonlib.agentweb.utils.AppUtils;

public class AliYunUtil {

    private static final String AccessKey = "LTAI4GAnWfhoMPRNg9zcGW76";
    private static final String Secret = "E3EpocDkJH6x6Rm4gEcOGSl2Zxs3Lq";
    private static final String Endpoint = "oss-cn-hangzhou.aliyuncs.com";
    private static final String Bucket = "huiboapp";

    public static OssService initOSS(UIDisplayer displayer) {

        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider("http://huibo.parkingquickly.com");
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(AppUtils.getApp(), Endpoint, credentialProvider, conf);
        OSSLog.enableLog();
        return new OssService(oss, Bucket, displayer);

    }
}
