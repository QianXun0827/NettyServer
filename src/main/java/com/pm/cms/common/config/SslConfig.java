package com.pm.cms.common.config;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * SSL工具类
 *
 * @author huhaiqiang
 * @date 2018/11/19 11:42
 */
public class SslConfig {

    public static SSLContext createSSLContext(String type , String path , String password) throws Exception {
        /// "JKS、PKCS12"
        KeyStore ks = KeyStore.getInstance(type);
        /// 证书存放地址
        InputStream ksInputStream = new FileInputStream(path);
        ks.load(ksInputStream, password.toCharArray());
        /*
        KeyManagerFactory充当基于密钥内容源的密钥管理器的工厂。
        getDefaultAlgorithm:获取默认的 KeyManagerFactory 算法名称。
         */
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());
        //  SSLContext的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);
        return sslContext;
    }

}
