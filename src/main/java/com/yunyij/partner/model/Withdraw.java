package com.yunyij.partner.model;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;
import com.github.jlf1997.spring_boot_sdk.oper.DBFinder;
import com.google.gson.annotations.Expose;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "td_partner_withdraw")
public class Withdraw extends BaseModel{
	
	public Withdraw() {
	
	}
	public Withdraw(Long creTime,Long updTime) {
		super(creTime,updTime);
	}

	@Id
	@ApiModelProperty(hidden=true)
	private Long withdrawId;
	
	/**
	 * 提现金额
	 */
	@ApiModelProperty(value="提现金额")
	private String amount;
	
	/**
	 * 提现状态
	 */
	@ApiModelProperty(value="提现状态")
	private Integer state;
	
	@ManyToOne
	@JoinColumn(name="partner_id",
	foreignKey= @ForeignKey(value=ConstraintMode.NO_CONSTRAINT,foreignKeyDefinition="none"))
	@Expose(serialize=false)
	@DBFinder(added=false)
	private Partner partner;//商家信息

	public Long getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(Long withdrawId) {
		this.withdrawId = withdrawId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
	
	
}
