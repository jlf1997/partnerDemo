package com.yunyij.partner.constant;

/**
 * Title: 常量   
 * @author Administrator
 *
 */
public class Constants {
	
	/**
	 * 系统盐值
	 */
	public static final String SYSTEM_SALT = "wgsh@yunyij";
	
	/**
	 * 用户盐值字符串长度
	 */
	public static final int USER_SALT_FIGURE_LENGTH=6;
	
	/**
	 * 用户初始密码长度
	 */
	public static final int USER_LOGIN_PWD_LENGTH = 8;
	
	/**
	 * 存储当前登录用户id的字段名
	 */
	public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

	/**
	 * token有效期（小时）
	 */
	public static final int TOKEN_EXPIRES_HOUR = 72;

	/**  存放Token的header字段   */      
//	public static final String DEFAULT_TOKEN_NAME = "X-Token";
	
	public static final String TOKEN_SIGNING_KEY = "gwang@yunyij/partner";
	

}
