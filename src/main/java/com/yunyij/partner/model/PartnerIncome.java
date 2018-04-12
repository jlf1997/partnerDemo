package com.yunyij.partner.model;

import java.util.Date;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;
import com.github.jlf1997.spring_boot_sdk.oper.DBFinder;
import com.github.jlf1997.spring_boot_sdk.oper.Oper;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "td_partner_income")
public class PartnerIncome extends BaseModel{
	
	@Id
	private Long id;
	
	private String inAmount;
	
	private String outAmount;
	
	/**
	 * 收入统计日期 ：精确到天
	 */
	@DBFinder(added=true,opType=Oper.BETWEEN)
	private Date logTime;
	
	/**
	 * 商店
	 */
	@ManyToOne
	@JoinColumn(name="shop_id",
	foreignKey= @ForeignKey(value=ConstraintMode.NO_CONSTRAINT,foreignKeyDefinition="none"))
	
	@DBFinder(added=false)
	private Shop shop;//商家信息

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getInAmount() {
		return inAmount;
	}

	public void setInAmount(String inAmount) {
		this.inAmount = inAmount;
	}

	public String getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(String outAmount) {
		this.outAmount = outAmount;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	
	
	
	

}
