package com.zy.community.utils;

import okhttp3.OkHttpClient;

import javax.net.ssl.*;

/**
 * 处理证书不受信任的问题
 */
public class OkHttpSSL {
    //okHttp3添加信任所有证书
    public static OkHttpClient getUnsafeOkHttpClient() {
    try {
        final TrustManager[] trustAllCerts = new TrustManager[]{
         new X509TrustManager() {
         @Override
         public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
         }
         @Override
         public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
         }
         @Override
         public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
         }
     }
    };
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sslSocketFactory);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
             return true;
            }
         });
            return builder.build();
    } catch (Exception e) {
        throw new RuntimeException(e);
        }
    }
}
