package com.company.project.common.utils;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * @Author: yj
 * @Date: 2021/1/8 11:22
 * @Desc: Jasypt加密解密工具类
 * @Version:
 */
public class JasyptUtil {
    /**
     * 加密
     *
     * @param password
     * @param value
     * @return
     */
    public static String encyptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.encrypt(value);
        return result;
    }

    /**
     * 解密
     *
     * @param password 配置文件中设定的加密密码
     * @param value    解密密文
     * @return
     */
    public static String decyptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.decrypt(value);
        return result;
    }

    /**
     * 配置加密
     * @param password
     * @return
     */
    public static SimpleStringPBEConfig cryptor(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }

    public static void main(String[] args) {
//加密
        System.out.println(encyptPwd("jasypt", "123456"));
        System.out.println(encyptPwd("jasypt", "root"));
//解密
        System.out.println(decyptPwd("jasypt", "lnzpDZItgjAntHqsYPFTew=="));
    }


}
