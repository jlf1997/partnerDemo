package com.yunyij.partner.model;

import java.util.List;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;
import com.github.jlf1997.spring_boot_sdk.oper.DBFinder;
import com.github.jlf1997.spring_boot_sdk.oper.Oper;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "td_partner_shop"
,uniqueConstraints= {@UniqueConstraint(columnNames= {"phone","delTime"})}
)
public class Shop extends BaseModel{

	
	public Shop() {
		
	}
	
	public Shop(Long creTime,Long updTime) {
		super(creTime,updTime);
	}
	/**
	 * 商户编号
	 */
	@Id
	private Long shopCode;
	

	
	
	/**
	 * 	营业执照公司名称
	 */
	@DBFinder(added=true,opType=Oper.LIKE)
	private String businessLicenseName;
	
	/**
	 * 营业执照编号
	 */
	private String businessLicenseCode;
	
	
	/**
	 * 营业执照图片地址
	 */
	private String businessLicenseImg;
	
	
	/**
	 * 身份证号码
	 */
	private String idCardNum;
	
	
	/**
	 * 身份证 名字
	 */
	private String idCardName;
	
	/**
	 * 身份证图片地址
	 */
	private String idCardImg;
	
	
	
	/**
	 * 联系电话
	 */
	private String phone;



	@ManyToOne
	@JoinColumn(name="partner_id",
	foreignKey= @ForeignKey(value=ConstraintMode.NO_CONSTRAINT,foreignKeyDefinition="none"))
	@Expose(serialize=false)
	@DBFinder(added=false)
	private Partner partner;//商家信息
	
	
	@OneToMany(mappedBy = "shop")
	@Expose(serialize=false)
	@org.hibernate.annotations.ForeignKey(name = "none")
	private List<Balance> balances;// 银行卡
	
	@OneToMany(mappedBy = "shop")
	@Expose(serialize=false)
	@org.hibernate.annotations.ForeignKey(name = "none")
	private List<PartnerIncome> partnerIncomes;// 银行卡
	


	/**
	 * 审核状态
	 */
	private Integer auditState;
	
	



	



	public List<Balance> getBalances() {
		return balances;
	}

	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}

	public Integer getAuditState() {
		return auditState;
	}



	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}



	public String getBusinessLicenseName() {
		return businessLicenseName;
	}



	public void setBusinessLicenseName(String businessLicenseName) {
		this.businessLicenseName = businessLicenseName;
	}



	public String getBusinessLicenseCode() {
		return businessLicenseCode;
	}



	public void setBusinessLicenseCode(String businessLicenseCode) {
		this.businessLicenseCode = businessLicenseCode;
	}



	public String getBusinessLicenseImg() {
		return businessLicenseImg;
	}



	public void setBusinessLicenseImg(String businessLicenseImg) {
		this.businessLicenseImg = businessLicenseImg;
	}



	public String getIdCardNum() {
		return idCardNum;
	}



	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}



	public String getIdCardImg() {
		return idCardImg;
	}



	public void setIdCardImg(String idCardImg) {
		this.idCardImg = idCardImg;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public Long getShopCode() {
		return shopCode;
	}



	public void setShopCode(Long shopCode) {
		this.shopCode = shopCode;
	}



	public String getIdCardName() {
		return idCardName;
	}



	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}



	public Partner getPartner() {
		return partner;
	}



	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public List<PartnerIncome> getPartnerIncomes() {
		return partnerIncomes;
	}

	public void setPartnerIncomes(List<PartnerIncome> partnerIncomes) {
		this.partnerIncomes = partnerIncomes;
	}
	
	
	

	
}
