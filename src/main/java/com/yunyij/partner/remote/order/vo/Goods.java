package com.yunyij.partner.remote.order.vo;



public class Goods implements Comparable<Goods>{
	/**
	 * 商品名称
	 */
	public String goodsname = "";
	/**
	 * 商品数量
	 */
	public Long num ;
	/**
	 * 商品单价
	 */
	public String price ;
	/**
	 * 超市商品id
	 */ 
	public Long goodsid;
	@Override
	public int compareTo(Goods o) {
		// TODO Auto-generated method stub
		
		return goodsid.compareTo(o.goodsid);
	}
}
