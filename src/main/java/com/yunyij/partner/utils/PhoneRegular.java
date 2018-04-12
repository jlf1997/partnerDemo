package com.yunyij.partner.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证手机号
 * 
 * @author Administrator
 *
 */
public class PhoneRegular {

	/**
	 * 正则验证手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean phoneVerify(String phone) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}
}
