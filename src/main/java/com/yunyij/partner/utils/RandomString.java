package com.yunyij.partner.utils;

import java.util.Random;

/**
 * 生成随机字符串
 * @author gwang
 *
 */
public class RandomString {
	public static final String SOURCES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

	/**
	 *  生成随机字符串
	 * @param length
	 * @return
	 */
	public static String generateString(int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = SOURCES.charAt(new Random().nextInt(SOURCES.length()));
		}
		return new String(text);
	}
}
