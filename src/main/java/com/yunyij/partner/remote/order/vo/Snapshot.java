package com.yunyij.partner.remote.order.vo;

import java.util.List;


/**
 * 商品快照对象
 * @author Administrator
 *
 */
public class Snapshot {
	/**
	 * 总金额
	 */
	transient private String totalPrice ;
	/**
	 * 配送信息
	 */
	private Courier courier;
	/**
	 * 商品列表
	 */
	private List<Goods> goods;
	
	
	private String goodsTotalPrice;
	
	
	/**
	 * 优惠列表
	 */
	private List<Coupon> coupons;
	
	
	private String couponsTotalPrice;
	
	
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Courier getCourier() {
		return courier;
	}
	public void setCourier(Courier courier) {
		this.courier = courier;
	}
	public List<Goods> getGoods() {
		return goods;
	}
	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}
	public List<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public String getGoodsTotalPrice() {
		return goodsTotalPrice;
	}
	public void setGoodsTotalPrice(String goodsTotalPrice) {
		this.goodsTotalPrice = goodsTotalPrice;
	}
	public String getCouponsTotalPrice() {
		return couponsTotalPrice;
	}
	public void setCouponsTotalPrice(String couponsTotalPrice) {
		this.couponsTotalPrice = couponsTotalPrice;
	}
	
	
	
	
	
	
	
	
}
