package com.yunyij.partner.remote.order.vo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderView  {

	/**
	 * 退货最后期限
	 */
	private Date backDeadLine;
	
	
	/**
	 * 订单完成时间
	 */
	private Date finishDate;
	
	private Long id;	

	private Long cstate;
	
	private String orderName;
	
	/**
	 * 创建时间
	 */
	private Date creTime;	
	/**
	 * 最后更新时间
	 */
	private Date  updTime;	
	/**
	 * 是否删除
	 */
	private Integer deleted ;
	private Long createBy;
	private Long modifiedBy;	
	private Long version;
	/**
	 * 订单编号
	 */
	private Long orderNum;	
	/**
	 * 会员id
	 */	
	private Long userid;
	/**
	 * 商店id
	 */
	private Long shopid;
	/**
	 * 会员实付金额
	 */
	private String totalPrice;
	/**
	 * 配送类型 0自提 1超市配送 2平台配送
	 */
	private Integer courierType;
	/**
	 * 配送人id
	 */
	private Long courierId;
	/**
	 * 订单状态
	 */
	private Integer orderState;	
	/**
	 * 订单状态描述
	 */
	private String orderStateName;	
	private Integer cancelState;	
	/**
	 * 商品快照
	 */
	private Snapshot snapshot;
	private Integer cancleState; 

	
	private String userName;
	
	private String shopName;
	
	
	private Date shopAcceptDeadLine;
	
	private Date payDeadLine;
	
	
	private String remark;
	
	
	private PayInfo payInfo;

	
	
	public Long getId() {
		return id;
	}






	public void setId(Long id) {
		this.id = id;
	}






	public Long getCstate() {
		return cstate;
	}






	public String getUserName() {
		return userName;
	}






	public String getRemark() {
		return remark;
	}






	public void setRemark(String remark) {
		this.remark = remark;
	}






	public void setUserName(String userName) {
		this.userName = userName;
	}






	public String getShopName() {
		return shopName;
	}






	public void setShopName(String shopName) {
		this.shopName = shopName;
	}






	public void setCstate(Long cstate) {
		this.cstate = cstate;
	}






	public Date getCreTime() {
		return creTime;
	}






	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}






	public Date getUpdTime() {
		return updTime;
	}






	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}






	public Integer getDeleted() {
		return deleted;
	}






	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}






	public Long getCreateBy() {
		return createBy;
	}






	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}






	public Long getModifiedBy() {
		return modifiedBy;
	}






	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}






	public Long getVersion() {
		return version;
	}






	public void setVersion(Long version) {
		this.version = version;
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






	public String getTotalPrice() {
		return totalPrice;
	}






	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}






	public Integer getCourierType() {
		return courierType;
	}






	public void setCourierType(Integer courierType) {
		this.courierType = courierType;
	}






	public Long getCourierId() {
		return courierId;
	}






	public void setCourierId(Long courierId) {
		this.courierId = courierId;
	}






	public Integer getOrderState() {
		return orderState;
	}






	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}






	public String getOrderStateName() {
		return orderStateName;
	}






	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}






	public Integer getCancelState() {
		return cancelState;
	}






	public void setCancelState(Integer cancelState) {
		this.cancelState = cancelState;
	}






	public Snapshot getSnapshot() {
		return snapshot;
	}






	public void setSnapshot(Snapshot snapshot) {
		this.snapshot = snapshot;
	}






	public Integer getCancleState() {
		return cancleState;
	}






	public void setCancleState(Integer cancleState) {
		this.cancleState = cancleState;
	}






	/**
	 * 判断前端传输的对象是否含有必须的字段
	 * @return
	 */
	public String  isSaveble() {
		//用户id 或 商店id 为空
		if(userid==null ) {
			return "userid is null";
		}
		if(shopid==null) {
			return "shopid is null";
		}
		/**
		 * 总价小于0
		 */
		if(new BigDecimal(totalPrice).intValue()<0) {
			return "totalPrice小于 0 ";
		}
		//商品信息不为空 但商品id为空
		if(snapshot==null || snapshot.getCourier()==null)
			return "goodid is null";
		
		if(snapshot.getGoods()!=null){
			for(Goods goodsE : snapshot.getGoods()) {
				if(goodsE.goodsid == null || goodsE.num<0) 
					return "goods num 小于 0 ";
			}			
		}
		return null;
	}






	public Date getShopAcceptDeadLine() {
		return shopAcceptDeadLine;
	}






	public void setShopAcceptDeadLine(Date shopAcceptDeadLine) {
		this.shopAcceptDeadLine = shopAcceptDeadLine;
	}






	public Date getPayDeadLine() {
		return payDeadLine;
	}






	public void setPayDeadLine(Date payDeadLine) {
		this.payDeadLine = payDeadLine;
	}






	public String getOrderName() {
		return orderName;
	}






	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}






	public PayInfo getPayInfo() {
		return payInfo;
	}






	public void setPayInfo(PayInfo payInfo) {
		this.payInfo = payInfo;
	}
	
	
	public Date getBackDeadLine() {
		return backDeadLine;
	}






	public void setBackDeadLine(Date backDeadLine) {
		this.backDeadLine = backDeadLine;
	}






	public Date getFinishDate() {
		return finishDate;
	}






	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}


	
}
