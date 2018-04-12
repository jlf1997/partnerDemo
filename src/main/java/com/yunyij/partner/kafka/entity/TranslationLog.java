package com.yunyij.partner.kafka.entity;

import com.yunyij.partner.remote.order.vo.BackGoods;
import com.yunyij.partner.remote.order.vo.OrderView;

public class TranslationLog {

	/**
	 * 
	 * 订单完成
	 */
	transient public static final int ORDER_FINISH = 1;
	
	
	
	/**
	 * 退货完成
	 */
	transient public static final int ORDER_BACK = 2;
	
	
	
	
	private int state;
	
	private Long marketid;
	
	private OrderView orderView;//1:orderView 2:GoodsBack
	
	private BackGoods backGoods;//1:orderView 2:GoodsBack
	
	
	private int tryCount;
	 
	public TranslationLog(OrderView orderView ) {
		this.setState(TranslationLog.ORDER_FINISH);
		this.setOrderView(orderView);
		this.setMarketid(orderView.getShopid());
	}
	
	public TranslationLog(BackGoods backGoods ) {
		this.setState(TranslationLog.ORDER_BACK);
		this.setBackGoods(backGoods);
		this.setMarketid(backGoods.getShopid());
	}
	

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Long getMarketid() {
		return marketid;
	}

	public void setMarketid(Long marketid) {
		this.marketid = marketid;
	}



	public int getTryCount() {
		return tryCount;
	}

	public void setTryCount(int tryCount) {
		this.tryCount = tryCount;
	}

	public OrderView getOrderView() {
		return orderView;
	}

	public void setOrderView(OrderView orderView) {
		this.orderView = orderView;
	}

	public BackGoods getBackGoods() {
		return backGoods;
	}

	public void setBackGoods(BackGoods backGoods) {
		this.backGoods = backGoods;
	}


	
	
	
	
	
	
	
}
