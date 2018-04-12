package com.yunyij.partner.remote.order.vo;

import javax.persistence.Id;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;

/**
 * 支付列表
 * @author Administrator
 *
 */

public class PayLog extends BaseModel{
	

	/**
	 * 支付不可提交
	 */
	transient public static final Long NOT = 1L;
	 
	/**
	 * 支付待提交
	 */
	transient public static final Long READEY = 2L;
	 
	/**
	 * 支付已提交
	 */
	transient public static final Long PAIED = 3L;
	
	/**
	 * 取消订单
	 */
	transient public static final Long CANCEL_ORDER = 1L;
	
	
	/**
	 * 退货
	 */
	transient public static final Long BACK_GOODS = 2L;
	
	@Id
	private Long payid;
	
	
	/**
	 * 支付金额
	 */
	private String amount;
	
	
	/**
	 * 关联订单号
	 */
	private Long orderNum;
	
	/**
	 * 用户id
	 */
	private Long userid;
	/**
	 * 商店id
	 */
	private Long shopid;
	
	
	/**
	 * 支付类型:1.支付宝 2.微信3.银行卡
	 */
	private Long payType;
	
	
	/**
	 * 支付状态 1.支付不可提交 2.支付待提交3.支付已提交
	 */
	private Long payState;
	
	
	/**
	 * 编号类型 ：1取消订单退款  2.退货退款
	 */
	private Long codeType;
	
	/**
	 * 对应退货单号，订单号 或者银行卡id
	 */
	private Long code;

	public Long getPayid() {
		return payid;
	}

	public void setPayid(Long payid) {
		this.payid = payid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}

	public Long getPayState() {
		return payState;
	}

	public void setPayState(Long payState) {
		this.payState = payState;
	}

	public Long getCodeType() {
		return codeType;
	}

	public void setCodeType(Long codeType) {
		this.codeType = codeType;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}
	
	public PayLog() {
		
	}

	
	

}
