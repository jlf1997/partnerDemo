package com.yunyij.partner.model;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;
import com.github.jlf1997.spring_boot_sdk.oper.DBFinder;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "td_partner_bankcard")
public class BankCard extends BaseModel{

	
	@Id
	private Long id;
	
	private String cardNum;// id
	
	private String username;// 用户名
	
	private String bankType;// 银行类型
	
	
	private String bankPro;// 开户省份
	
	private String bankCity;//开户城市
	
	private String region;//开户区

	private String branch;//开户支行
	
	@ManyToOne
	@JoinColumn(name="partner_id",
	foreignKey= @ForeignKey(value=ConstraintMode.NO_CONSTRAINT,foreignKeyDefinition="none"))
	@Expose(serialize=false)
	@DBFinder(added=false)
	private Partner partner;//商家信息

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankPro() {
		return bankPro;
	}

	public void setBankPro(String bankPro) {
		this.bankPro = bankPro;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	
	
	
	
}
