package com.yunyij.partner.vo;

import java.util.List;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;
import com.yunyij.partner.model.Area;
import com.yunyij.partner.model.BankCard;
import com.yunyij.partner.model.Partner;

import io.swagger.annotations.ApiModel;

/**
 * 视图表
 * @author gwang
 *
 */
@ApiModel(value="Partner",description="合伙人信息")
public class PartnerVO extends Partner{
	
	
	
	/**
	 * 省
	 */
	private Area province;
	
	/**
	 * 市
	 */
	private Area city;
	
	/**
	 * 县
	 */
	private Area county;
	
	
	
	private String token;



	public Area getProvince() {
		return province;
	}



	public void setProvince(Area province) {
		this.province = province;
	}



	public Area getCity() {
		return city;
	}



	public void setCity(Area city) {
		this.city = city;
	}



	public Area getCounty() {
		return county;
	}



	public void setCounty(Area county) {
		this.county = county;
	}



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}
	
	
	

	
	
	
	

	
}
