package com.yunyij.partner.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;
@Entity
@Table(name = "td_Phone_Message_code")
public class PhoneMessageCode extends BaseModel{
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String phone;
	
	private String code;
	
	private String ip;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	

}
