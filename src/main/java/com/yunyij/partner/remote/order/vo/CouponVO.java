package com.yunyij.partner.remote.order.vo;
import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 优惠券
 * 
 * @author Administrator
 *
 */
public class CouponVO implements Serializable {

	private static final long serialVersionUID = 5111503599318262651L;

	private Long id;

	@Max(value = 50, message = "优惠券名称长度不能超过50个字符")
	private String couponName;// 优惠券名称

	private int couponType; // 优惠券类型 （0平台优惠券，1商家优惠券）

	private long marketId; // 商家编号， 0（平台）

	@Max(value = 50, message = "商家名称长度不能超过50个字符")
	private String marketName;// 商家名称

	private int total; // 总数量

	@Max(value = 10, message = "优惠券金额长度不能超过10个字符")
	private String couponMoney; // 优惠券金额

	private int grantWay; // 发放方式（1平台主动发放 2用户领取）

	@Max(value = 255, message = "描述长度不能超过255个字符")
	private String couponDesc; // 描述

	private int grantLimit; // 0 (只能领一次) 1（活动期间每天只能领取一次）

	private int useLimit = 1;// 使用限制：每笔订单使用张数

	@Max(value = 10, message = "最低消费金额长度不能超过10个字符")
	private String useLimitMoney; // 最低消费金额（例如满10元使用）

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date grantLimitStartTime; // 发放开始时间

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date grantLimitEndTime; // 发放结束时间

	private int validDays; // 有效天数

	private int audit; // 状态： 0：初始状态 1（审核中） 2：审核通过 -2：审核未通过

	private int grantNum; // 已发放数量

	private int usedNum; // 已使用数量

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date creTime;// 创建时间
	
	

	public CouponVO() {
		super();
	}

	public CouponVO(Long id, String couponName, int couponType, long marketId, String marketName, int total,
			String couponMoney, int grantWay, String couponDesc, int grantLimit, int useLimit, String useLimitMoney,
			Date grantLimitStartTime, Date grantLimitEndTime, int validDays, int audit, int grantNum, int usedNum,
			Date creTime) {
		super();
		this.id = id;
		this.couponName = couponName;
		this.couponType = couponType;
		this.marketId = marketId;
		this.marketName = marketName;
		this.total = total;
		this.couponMoney = couponMoney;
		this.grantWay = grantWay;
		this.couponDesc = couponDesc;
		this.grantLimit = grantLimit;
		this.useLimit = useLimit;
		this.useLimitMoney = useLimitMoney;
		this.grantLimitStartTime = grantLimitStartTime;
		this.grantLimitEndTime = grantLimitEndTime;
		this.validDays = validDays;
		this.audit = audit;
		this.grantNum = grantNum;
		this.usedNum = usedNum;
		this.creTime = creTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public int getCouponType() {
		return couponType;
	}

	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}

	public long getMarketId() {
		return marketId;
	}

	public void setMarketId(long marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(String couponMoney) {
		this.couponMoney = couponMoney;
	}

	public int getGrantWay() {
		return grantWay;
	}

	public void setGrantWay(int grantWay) {
		this.grantWay = grantWay;
	}

	public String getCouponDesc() {
		return couponDesc;
	}

	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}

	public int getGrantLimit() {
		return grantLimit;
	}

	public void setGrantLimit(int grantLimit) {
		this.grantLimit = grantLimit;
	}

	public int getUseLimit() {
		return useLimit;
	}

	public void setUseLimit(int useLimit) {
		this.useLimit = useLimit;
	}

	public String getUseLimitMoney() {
		return useLimitMoney;
	}

	public void setUseLimitMoney(String useLimitMoney) {
		this.useLimitMoney = useLimitMoney;
	}

	public Date getGrantLimitStartTime() {
		return grantLimitStartTime;
	}

	public void setGrantLimitStartTime(Date grantLimitStartTime) {
		this.grantLimitStartTime = grantLimitStartTime;
	}

	public Date getGrantLimitEndTime() {
		return grantLimitEndTime;
	}

	public void setGrantLimitEndTime(Date grantLimitEndTime) {
		this.grantLimitEndTime = grantLimitEndTime;
	}

	public int getAudit() {
		return audit;
	}

	public void setAudit(int audit) {
		this.audit = audit;
	}

	public int getGrantNum() {
		return grantNum;
	}

	public void setGrantNum(int grantNum) {
		this.grantNum = grantNum;
	}

	public int getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(int usedNum) {
		this.usedNum = usedNum;
	}

	public int getValidDays() {
		return validDays;
	}

	public void setValidDays(int validDays) {
		this.validDays = validDays;
	}

}
