package com.yunyij.partner.remote.order.vo;

public class OrderStateType {
		
	/**
	 * 1待支付,等待用户支付
	 */
	public static final int  TOBEPAID = 1;
	
	/**
	 * 2待接单，等待商家接单
	 */
	public static final int  TOBETAKE = 2;
	
	/**
	 * 3待发货，等待商家发货
	 */
	public static final int  TOBESEND = 4;
	
	/**
	 * 4配送中
	 */
	public static final int  SENDING = 8;
	
	/**
	 * 5待取货，等待用户自提
	 */
	public static final int  TOBERECIVE = 16;
	
	
	/**
	 * 6订单完成
	 */
	public static final int  FINISH = 32;
	
	
	/**
	 * 7用户取消订单
	 */
	public static final int  USERCANCEL = 64;
	
	
	/**
	 * 8商家取消订单
	 */
	public static final int  BUSESSCANCEL = 128;
	
	
	/**
	 * 9待配送，等待配送人员配送
	 */
	public static final int  WAITSEND = 256;
	
	/**
	 * 10用户拒单
	 */
	public static final int USERREFUSE  = 512;
	
	/**
	 * 当前最大码
	 */
	private static final int MAX = USERREFUSE;
	
	/**
	 * 获取所有状态码，最大值
	 * @return
	 */
	public static int getAllCode() {
		return MAX*2-1;
	}
	
	
	public static String getDes(int type) {
		String des = "";
		switch(type) {
		case TOBEPAID : 
			des = "待支付";
			break;
		case TOBETAKE : 
			des = "待接单";
			break;
		case TOBESEND : 
			des = "待发货";
			break;
		case SENDING : 
			des = "配送中";
			break;
		case TOBERECIVE : 
			des = "待取货";
			break;
		case FINISH : 
			des = "订单完成";
			break;
		case USERCANCEL : 
			des = "用户取消订单";
			break;
		case BUSESSCANCEL : 
			des = "商家取消订单";
			break;
		case WAITSEND : 
			des = "待配送";
			break;			
		case USERREFUSE : 
			des = "用户拒单";
			break;
		}
		return des;
	}
	
		
}
