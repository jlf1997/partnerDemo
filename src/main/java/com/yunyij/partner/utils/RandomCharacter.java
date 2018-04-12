package com.yunyij.partner.utils;

public class RandomCharacter {

	/**
	 * 生成3位随机小写字符
	 * 
	 * @return
	 */
	public static String getCharacter() {
		StringBuffer sb = new StringBuffer();
		String str = "abcdefghijklmnopqrstuvwxyz";
		char[] ch = new char[3];
		for (int i = 0; i < ch.length; i++) {
			int rand = (int) (Math.random() * str.length());
			ch[i] = str.charAt(rand);
		}
		for (int i = 0; i < ch.length; i++) {
			sb.append(ch[i]);
		}
		return sb.toString();
	}

	/**
	 * 随机生成6位验证码
	 * 
	 * @param messageCode
	 * @return
	 */
	public static String getMessageCode() {
		String messageCode = String.format("%06d", (int) ((Math.random() * 9 + 1) * 100000));
		return messageCode;
	}
	
	/**
	 * 随机生成4位验证码
	 * 
	 * @param messageCode
	 * @return
	 */
	public static String getCode() {
		String messageCode = String.format("%04d", (int) ((Math.random() * 9 + 1) * 1000));
		return messageCode;
	}
	
}
