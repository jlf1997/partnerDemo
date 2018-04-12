package com.yunyij.partner.remote.order.vo;


/**
 * 支付信息
 * @author Administrator
 *
 */
public class PayInfo {

	
	/**
	 * 阿里支付
	 */
	transient public int ALI_PAY = 1;
	/**
	 * 微信支付
	 */
	transient public int WEI_CHAT_PAY = 2;
	
	
	
	/**
	 * 支付流水号
	 */
	private Long payNum;
	/**
	 * 订单号
	 */
	private Long orderNum;
	
	/**
	 * 支付方式
	 */
	private Integer payType;
	
	/**
	 * 支付时间
	 */
	private Long payTime;
	
	/**
	 * 支付金额
	 */
	private String amount;

	public Long getPayNum() {
		return payNum;
	}

	public void setPayNum(Long payNum) {
		this.payNum = payNum;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}



	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}
	
	
	
	
}
