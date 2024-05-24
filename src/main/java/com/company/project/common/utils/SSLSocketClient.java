package com.company.project.common.utils;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * SSLSocketClient
 * @author wenbin.li
 */
public class SSLSocketClient {
    //获取这个SSLSocketFactory
    //通过这个类我们可以获得SSLSocketFactory，这个东西就是用来管理证书和信任证书的
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    //获取TrustManager
    private static TrustManager[] getTrustManager() {
        //不校检证书链
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        //不校检客户端证书
                    }
 
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        //不校检服务器证书
                    }
 
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                        //OKhttp3.0以前返回null,3.0以后返回new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }
 
    //获取HostnameVerifier
    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                //未真正校检服务器端证书域名
                return true;
            }
        };
        return hostnameVerifier;
    }
}