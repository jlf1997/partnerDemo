package com.yunyij.partner.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;
import com.github.jlf1997.spring_boot_sdk.oper.DBFinder;
import com.github.jlf1997.spring_boot_sdk.oper.Oper;
import com.google.gson.annotations.Expose;

/**
 * 合伙人表
 * 
 * @author gwang
 *
 */
@Entity
@Table(name = "td_partner_base"
,uniqueConstraints= {@UniqueConstraint(columnNames= {"phone","delTime"})})
public class Partner extends BaseModel {

	/**
	 * 合伙人编号
	 */
	@Id
	@Column(length=20)
	private Long partnerId;

	/**
	 * 姓名
	 */
	@Column(length=50)
	private String name;
	
	/**
	 * 身份证图片地址
	 */
	private String idCardsImg;
	
	/**
	 * 身份证号码
	 */
	private String idCardsNum;
	
	
	/**
	 * 所在城市id
	 */
	private Long cityCode;
	
	
	
	
	/**
	 * 联系地址
	 */
	@DBFinder(opType=Oper.LIKE)
	private String address;
	
	

	/**
	 * 联系电话
	 */
	@Column(length=11)
	private String phone;// 手机号

	/**
	 * 登陆密码
	 */
	@Column(length=32)
	@Expose(serialize=false)
	private String loginPwd;

	/**
	 * 盐值
	 */
	@Column(length=6)
	@Expose(serialize=false)
	private String saltFigure;
	/**
	 * 所属公司ID
	 */
	private Long branchId;

	
	/**
	 * 推荐人编号（合伙人编号）
	 */
	private Long refereeCode;

	/**
	 *  审核状态：0： 未审核；1：审核通过
	 */
	private Integer status;
	
	/**
	 * 是否禁用 true禁用 false未禁用
	 */
	private Boolean disable;

	
	
	
	@OneToMany(mappedBy = "partner")
	@org.hibernate.annotations.ForeignKey(name = "none")
	private List<BankCard> bankcards;// 银行卡
	
	
	@OneToMany(mappedBy = "partner")
	@org.hibernate.annotations.ForeignKey(name = "none")
	private List<Shop> shops;// 银行卡
	
	
	@OneToMany(mappedBy = "partner")
	@org.hibernate.annotations.ForeignKey(name = "none")
	private List<Withdraw> withdraws;// 银行卡
	
	/**
	 * 可提取余额
	 */
	private String balance;
	
	
	/**
	 * 所有余额
	 */
	private String balanceAll;

	public Long getPartnerId() {
		return partnerId;
	}



	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}



	public Partner() {
		super();
	}
	
	public Partner(Long creTime,Long updTime) {
		super(creTime,updTime);
		
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getSaltFigure() {
		return saltFigure;
	}

	public void setSaltFigure(String saltFigure) {
		this.saltFigure = saltFigure;
	}

	public Long getBranchId() {
		return branchId;
	}

	





	public Integer getStatus() {
		return status;
	}

	public String getIdCardsImg() {
		return idCardsImg;
	}

	public void setIdCardsImg(String idCardsImg) {
		this.idCardsImg = idCardsImg;
	}

	public String getIdCardsNum() {
		return idCardsNum;
	}

	public void setIdCardsNum(String idCardsNum) {
		this.idCardsNum = idCardsNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getRefereeCode() {
		return refereeCode;
	}

	public void setRefereeCode(Long refereeCode) {
		this.refereeCode = refereeCode;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<BankCard> getBankcards() {
		return bankcards;
	}

	public void setBankcards(List<BankCard> bankcards) {
		this.bankcards = bankcards;
	}



	public Boolean getDisable() {
		return disable;
	}



	public void setDisable(Boolean disable) {
		this.disable = disable;
	}



	public Long getCityCode() {
		return cityCode;
	}



	public void setCityCode(Long cityCode) {
		this.cityCode = cityCode;
	}



	public List<Shop> getShops() {
		return shops;
	}



	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}



	public String getBalance() {
		return balance;
	}



	public void setBalance(String balance) {
		this.balance = balance;
	}



	public String getBalanceAll() {
		return balanceAll;
	}



	public void setBalanceAll(String balanceAll) {
		this.balanceAll = balanceAll;
	}



	public List<Withdraw> getWithdraws() {
		return withdraws;
	}



	public void setWithdraws(List<Withdraw> withdraws) {
		this.withdraws = withdraws;
	}


	
	
	
	

}
