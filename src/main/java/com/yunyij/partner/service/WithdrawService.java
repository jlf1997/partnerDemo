package com.yunyij.partner.service;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.jlf1997.spring_boot_sdk.service.FindBase;
import com.yunyij.partner.constant.SubmitState;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.model.Shop;
import com.yunyij.partner.model.Withdraw;
import com.yunyij.partner.repository.WithdrawJpa;
import com.yunyij.partner.utils.IdGener;

@Service("withdrawService")
public class WithdrawService extends FindBase<Withdraw, Long>{
	
	@Autowired
	private WithdrawJpa withdrawJpa;
	
	@Autowired
	private PartnerService partnerService;
	
	/**
	 * 提现
	 * @return
	 */
	@Transactional
	public String withdraw(Partner obj,BigDecimal am ) {
		Assert.notNull(obj, "partner is null");
		Assert.notNull(am, "提现金额 is null");
		//获取可提现金额
		BigDecimal balance = new BigDecimal(obj.getBalance()==null?"0":obj.getBalance());
		BigDecimal balanceAll = new BigDecimal(obj.getBalanceAll()==null?"0":obj.getBalanceAll());
		//提现金额大于可提现金额
		if(balance.compareTo(am)<0) {
			return "提现金额大于可提现金额";
		}
		balance = balance.subtract(am);
		balanceAll = balanceAll.subtract(am);
		obj.setBalance(balance.toString());
		obj.setBalanceAll(balanceAll.toString());
		partnerService.save(obj);
		Withdraw withdraw = new Withdraw();
		withdraw.setWithdrawId(IdGener.getInstance().getId());
		withdraw.setAmount(am.toString());
		withdraw.setState(SubmitState.SUBMIT);
		withdraw.setPartner(obj);
		save(withdraw);
		return null;
		
	}

	@Override
	public JpaSpecificationExecutor<Withdraw> specjpa() {
		// TODO Auto-generated method stub
		return withdrawJpa;
	}

	@Override
	public JpaRepository<Withdraw, Long> jpa() {
		// TODO Auto-generated method stub
		return withdrawJpa;
	}

	@Override
	public void addWhere(Withdraw[] t, List<Predicate> predicates, Root<Withdraw> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		if(t!=null && t.length>0) {
			Partner partner = t[0].getPartner();
			if(partner!=null) {
				Long partnerId = partner.getPartnerId();
				if(partnerId!=null) {
					Join<Shop, Partner> withdrawJoinPartner = root.join("partner", JoinType.LEFT);
					predicates.add(cb.equal(withdrawJoinPartner.get("partnerId"), partnerId)); 
				}
			}
		}
	}

	@Override
	public void setSelect(Withdraw t) {
		// TODO Auto-generated method stub
		
	}

}
