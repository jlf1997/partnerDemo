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
import com.yunyij.partner.model.BankCard;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.repository.BankCardJpa;

@Service("bankCardService")
public class BankCardService extends FindBase<BankCard, Long>{
	
	@Autowired
	private BankCardJpa bankCardJpa;

	@Override
	public JpaSpecificationExecutor<BankCard> specjpa() {
		// TODO Auto-generated method stub
		return bankCardJpa;
	}

	@Override
	public JpaRepository<BankCard, Long> jpa() {
		// TODO Auto-generated method stub
		return bankCardJpa;
	}

	@Override
	public void addWhere(BankCard[] t, List<Predicate> predicates, Root<BankCard> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		if(t!=null && t.length>0) {
			Partner partner = t[0].getPartner();
			if(partner!=null) {
				Long partnerId = partner.getPartnerId();
				if(partnerId!=null) {
					Join<BankCard, Partner> bankCardJoinPartner = root.join("partner", JoinType.LEFT);
					predicates.add(cb.equal(bankCardJoinPartner.get("partnerId"), partnerId)); 
				}
			}
		}
	}

	@Override
	public void setSelect(BankCard t) {
		// TODO Auto-generated method stub
		
	}

}
