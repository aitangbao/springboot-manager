package com.company.project.common.utils;

import java.util.UUID;


/**
 * 密码工具类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public class PasswordUtils {

	/**
	 * 匹配密码
	 * @param salt 盐
	 * @param rawPass 明文 
	 * @param encPass 密文
	 * @return 是否匹配
	 */
	public static boolean matches(String salt, String rawPass, String encPass) {
		return new PasswordEncoder(salt).matches(encPass, rawPass);
	}
	
	/**
	 * 明文密码加密
	 * @param rawPass 明文
	 * @param salt 盐
	 * @reture 加密后
	 */
	public static String encode(String rawPass, String salt) {
		return new PasswordEncoder(salt).encode(rawPass);
	}

	/**
	 * 获取加密盐
	 * @return 盐值
	 */
	public static String getSalt() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
	}

}
