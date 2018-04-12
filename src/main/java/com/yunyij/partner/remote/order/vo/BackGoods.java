package com.yunyij.partner.remote.order.vo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;
import com.google.gson.annotations.Expose;

/**
 * 退货单表
 * @author Administrator
 *
 */


public class BackGoods extends BaseModel{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	

	
	/**
	 * 退单号
	 */
	private Long backNum;
	
	/**
	 * 退单状态
	 */
	private Integer backState;
	
//	@Transient
//	private List<Goods> goods;
//	
//	@Transient
//	private Courier courier;
	
	
	@Transient
	private Snapshot snapshot;
	
	/**
	 * 备注
	 */
	private String remark;
	
	

	
	
	/**
	 * 会员id
	 */
	
	private Long userid;
	
	/**
	 * 商店id
	 */
	private Long shopid;
	
	
	/**
	 * 退货金额
	 */
	private String totalPrice;
	
	
	
	private Long orderNum;
	

	/**
	 * 退货商品列表json
	 */
	@Expose(serialize=false,deserialize=false)
	@Lob
	@Column(name="goods")
	private String snapshotStr;
	
	

	public Long getBackNum() {
		return backNum;
	}


	public void setBackNum(Long backNum) {
		this.backNum = backNum;
	}


	public Integer getBackState() {
		return backState;
	}


	public void setBackState(Integer backState) {
		this.backState = backState;
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


	public String getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}







	public String getSnapshotStr() {
		return snapshotStr;
	}


	public void setSnapshotStr(String snapshotStr) {
		this.snapshotStr = snapshotStr;
	}


	public Long getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}




	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Snapshot getSnapshot() {
		return snapshot;
	}


	public void setSnapshot(Snapshot snapshot) {
		this.snapshot = snapshot;
	}




	
	

	
	
	
	
}
