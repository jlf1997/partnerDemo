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
import com.yunyij.partner.model.PartnerIncome;
import com.yunyij.partner.model.Shop;
import com.yunyij.partner.repository.PartnerIncomeJpa;

@Service("partnerIncomeService")
public class PartnerIncomeService extends FindBase<PartnerIncome, Long>{
	
	@Autowired
	private PartnerIncomeJpa partnerIncomeJpa;

	@Override
	public JpaSpecificationExecutor<PartnerIncome> specjpa() {
		// TODO Auto-generated method stub
		return partnerIncomeJpa;
	}

	@Override
	public JpaRepository<PartnerIncome, Long> jpa() {
		// TODO Auto-generated method stub
		return partnerIncomeJpa;
	}

	@Override
	public void addWhere(PartnerIncome[] t, List<Predicate> predicates, Root<PartnerIncome> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		if(t!=null && t.length>0) {
			Shop shop = t[0].getShop();
			if(shop!=null) {
				Partner partner = shop.getPartner();
				if(partner!=null) {//按照合伙人id查询
					Long partnerId = partner.getPartnerId();
					if(partnerId!=null) {
						Join<Shop, Partner> balanceJoinShop = root.join("shop", JoinType.LEFT);
						Join<Shop, Partner> shopJoinPartner = balanceJoinShop.join("partner", JoinType.LEFT);
						predicates.add(cb.equal(shopJoinPartner.get("partnerId"), partnerId)); 
					}
				}else if(shop.getShopCode()!=null){//按照不同商店查询
					Join<Shop, Partner> balanceJoinShop = root.join("shop", JoinType.LEFT);
					predicates.add(cb.equal(balanceJoinShop.get("shopCode"), shop.getShopCode())); 
				}
			}			
			
		}
		
	}

	@Override
	public void setSelect(PartnerIncome t) {
		// TODO Auto-generated method stub
		
	}

}
