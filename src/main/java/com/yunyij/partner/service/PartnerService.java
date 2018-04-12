package com.yunyij.partner.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.github.jlf1997.spring_boot_sdk.service.FindBase;
import com.yunyij.partner.constant.Constants;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.model.Shop;
import com.yunyij.partner.repository.PartnerRepository;
import com.yunyij.partner.utils.IdGener;
import com.yunyij.partner.utils.MD5Util;
import com.yunyij.partner.utils.RandomString;

@Service("partnerService")
public class PartnerService extends FindBase<Partner, Long>{

	@Autowired
	private PartnerRepository repository;

	
	

	
	/**
	 * 根据电话查询用户
	 * @param phone
	 * @return
	 */
	public Partner findByPhone(String phone) {
		Partner p = new Partner();
		p.setPhone(phone);	
		return find(p);
	}

	/**
	 * 验证合伙人是否有效
	 * @return
	 */
	public String checkPartnerDisable(Partner p) {
		if(p==null) {
			return "用户不存在";
		}
		if(p.getDisable()) {
			return "用户已被禁用";
		}
		return null;
	}
	
	
	/**
	 * 根据合伙人
	 */
	public Partner login(String code, String password) {
		
		return null;
	}

	@Override
	public void addWhere(Partner[] t, List<Predicate> predicates, Root<Partner> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	
		
	}

	@Override
	public JpaRepository<Partner, Long> jpa() {
		// TODO Auto-generated method stub
		return repository;
	}

	@Override
	public void setSelect(Partner arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JpaSpecificationExecutor<Partner> specjpa() {
		// TODO Auto-generated method stub
		return repository;
	}
}
