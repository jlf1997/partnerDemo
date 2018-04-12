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
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.model.Shop;
import com.yunyij.partner.repository.ShopJpa;

@Service("shopService")
public class ShopService extends FindBase<Shop, Long>{
	
	@Autowired
	private ShopJpa shopJpa;

	@Override
	public JpaSpecificationExecutor<Shop> specjpa() {
		// TODO Auto-generated method stub
		return shopJpa;
	}

	@Override
	public JpaRepository<Shop, Long> jpa() {
		// TODO Auto-generated method stub
		return shopJpa;
	}

	@Override
	public void addWhere(Shop[] t, List<Predicate> predicates, Root<Shop> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		if(t!=null && t.length>0) {
			Partner partner = t[0].getPartner();
			if(partner!=null) {
				Long partnerId = partner.getPartnerId();
				if(partnerId!=null) {
					Join<Shop, Partner> shopJoinPartner = root.join("partner", JoinType.LEFT);
					predicates.add(cb.equal(shopJoinPartner.get("partnerId"), partnerId)); 
				}
			}
		}
		
		
	}

	@Override
	public void setSelect(Shop t) {
		// TODO Auto-generated method stub
		
	}
	
	

}
