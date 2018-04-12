package com.yunyij.partner.remote.order.vo;

import java.util.Date;

/**
 * 配送信息
 * @author Administrator
 *
 */
public class Courier {
	/**
	 * 客户自提
	 */
	
	transient  public static final int USER = 0;
	/**
	 * 商家配送
	 */
	transient  public static final int SHOP = 1;
	/**
	 * 平台配送
	 */
	transient  public static final int PLATFORM = 2;
	
	
	/**
	 * 配送类型
	 */
	public int courierType;
	/**
	 * 收货人姓名
	 */
	public String name;
	/**
	 * 收货人联系电话
	 */
	public String phone;
	
	/**
	 * 收货地址
	 */
	public String address;
	
	/**
	 * 配送时间，null表示尽快送达
	 */
	public Date time;
	
	
	/**
	 * 配送费
	 */
	public String fee;

	
}
