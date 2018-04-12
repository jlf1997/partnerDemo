package com.yunyij.partner.remote.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠信息
 * @author Administrator
 *
 */
public class Coupon {
	
	/**
	 * 最后优惠总额 正数
	 */
	private String couponAmount;
	
	/**
	 * 优惠对应唯一编号
	 */
	private Long couponId;
	
	/**
	 * 优惠类型：0平台 1商家
	 */
	private Integer couponType;



	public String getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}
	
	

}
