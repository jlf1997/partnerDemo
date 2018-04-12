package com.yunyij.partner.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.jlf1997.spring_boot_sdk.service.FindBase;
import com.yunyij.partner.model.Balance;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.model.PartnerIncome;
import com.yunyij.partner.model.Shop;
import com.yunyij.partner.remote.shop.vo.ShopBalance;
import com.yunyij.partner.repository.BalanceJpa;
import com.yunyij.partner.utils.IdGener;
import com.yunyij.partner.utils.TimeUtilV;

@Service("balanceService")
public class BalanceService extends FindBase<Balance, Long>{
	
	@Autowired
	private BalanceJpa balanceJpa;
	
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private PartnerIncomeService partnerIncomeService;
	
	
	@Value("${shop.rate:97}")
	public  String rate1 ;
	


	/**
	 * 变更合伙人账户余额
	 * @param tra
	 * @return
	 */
	@Transactional
	public BigDecimal updateShopAccountBalance(Balance tra) {
		// TODO Auto-generated method stub

		Shop shop = new Shop();
		shop.setShopCode(tra.getShop().getShopCode());
		Shop obj = shopService.find(shop);
		Partner partner = obj.getPartner();
		BigDecimal amount;
		if(partner.getBalanceAll()==null) {
			 amount = new BigDecimal("0");
		}else {
			 amount = new BigDecimal(partner.getBalanceAll());
		}
		
		BigDecimal rate ;
		if(tra.getTransactionType().equals(Balance.transactionType_OUT)) {
			rate = new BigDecimal("1.0");
		}else {
			rate = getRealAmountByRate();
		}
		BigDecimal superMarketBalance = tra.getShopAllBalance(rate,amount);  
		
		partner.setBalanceAll(superMarketBalance.toString());
		partnerService.save(partner);
		
    	tra.setShop(obj);
    	tra.setState(0);
    	save(tra);
			
		return  superMarketBalance;
	}
	
	 
    /**
     * 获取平台比例，换算为商家费率
     * @return
     */
    private BigDecimal getRealAmountByRate() {
		BigDecimal rate;
		if(rate1!=null) {
			BigDecimal r;
			try {
				 r =  new BigDecimal(rate1);
			}catch (Exception e) {
				r =  new BigDecimal("97");
			}			
			rate = new BigDecimal("100").subtract(r).multiply(new BigDecimal("0.01")); 
		}else {
			rate = new BigDecimal("0.03");
		}
		return rate;		
	}

	@Override
	public JpaSpecificationExecutor<Balance> specjpa() {
		// TODO Auto-generated method stub
		return balanceJpa;
	}

	@Override
	public JpaRepository<Balance, Long> jpa() {
		// TODO Auto-generated method stub
		return balanceJpa;
	}

	@Override
	public void addWhere(Balance[] t, List<Predicate> predicates, Root<Balance> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
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
	public void setSelect(Balance t) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 获取最新可提现金额
	 */
	@Transactional
	public String getBalance(Partner partner) {	
		if(partner==null) {
			return null;
		}
		Shop shop = new Shop();
		shop.setPartner(partner);
		
		Balance balanceS = new Balance();
		balanceS.setShop(shop);
		balanceS.setState(0);
		List<Balance> balances = this.findAll(balanceS);
		BigDecimal amount = new BigDecimal(partner.getBalance()==null?"0":partner.getBalance());
		//
		long curruent = new Date().getTime();			
		curruent-=1*24*60*60*1000;
		Date da = new Date(curruent);
		for(Balance balance:balances) {
			if(balance.getCreTime().before(da)  
					&& Balance.transactionType_IN==balance.getTransactionType()) {
				amount = amount.add(new BigDecimal(balance.getAmount()));
			}
			if(Balance.transactionType_BACK==balance.getTransactionType()
					||Balance.transactionType_OUT==balance.getTransactionType()) {
				amount = amount.subtract(new BigDecimal(balance.getAmount()));
			}
			//修改记录：变为已处理
			balance.setState(1);
			this.save(balance);
		}
		String amountStr = amount.toString();
		partner.setBalance(amountStr);
		partnerService.save(partner);
		return amountStr;
		
	}
	
	/**
	 * 获取每日收入
	 * @param partner
	 * @return
	 */
	public void getTodayIncomeAndBack(Partner partner) {
		if(partner==null) {
			return ;
		}
		BigDecimal amount = new BigDecimal(partner.getBalance()==null?"0":partner.getBalance());
		BigDecimal amountAll = new BigDecimal(partner.getBalanceAll()==null?"0":partner.getBalanceAll());
	
		
		Shop shopF = new Shop();
		shopF.setPartner(partner);
		List<Shop> shops = shopService.findAll(shopF);
		
		List<ShopBalance> shopBalanceList = new ArrayList<>();
		ShopBalance shopBalance = new ShopBalance();
		for(Shop shop:shops) {
			shopBalance = new ShopBalance();
			shopBalance.setShopId(shop.getShopCode());
			shopBalanceList.add(shopBalance);	
		}
		List<ShopBalance> shopBalanceGets = getShopBalance(TimeUtilV.getDateStartTime(TimeUtilV.getYesterDay())
				,TimeUtilV.getDateEndTime(TimeUtilV.getYesterDay()),shopBalanceList);
		
		List<PartnerIncome> partnerIncomeList = new ArrayList<>();
		PartnerIncome partnerIncome;
		for(ShopBalance shopBalanceGet :shopBalanceGets) {
			partnerIncome = new PartnerIncome();
			Shop shop = new Shop();
			shop.setShopCode(shopBalanceGet.getShopId());
			Shop getShop = shopService.find(shop);
			if(!checkIncomeIsSaved(getShop,TimeUtilV.getDateStartTime(TimeUtilV.getYesterDay()))) {
				partnerIncome.setShop(getShop);
				partnerIncome.setId(IdGener.getInstance().getId());
				partnerIncome.setInAmount(shopBalanceGet.getIncomeAmount());
				partnerIncome.setOutAmount(shopBalanceGet.getBackAmount());
				partnerIncome.setLogTime(TimeUtilV.getDateStartTime(TimeUtilV.getYesterDay()));
				partnerIncomeList.add(partnerIncome);
			}
			
		}
		partnerIncomeService.save(partnerIncomeList);
		
		
		partner.setBalance(amount.toString());
		partner.setBalanceAll(amountAll.toString());
		partnerService.save(partner);
		//更新余额
		
		
		
	}
	
	
	/**
	 * 获取指定日期内的收入与支出
	 * @param begian
	 * @param end
	 * @param balances
	 * @return
	 */
	public List<ShopBalance> getShopBalance(Date begian,Date end,List<ShopBalance> shopBalances){
		Assert.notNull(shopBalances, "shopBalances is not null");
		List<ShopBalance> rest = new ArrayList<>();
//		long curruent = new Date().getTime();			
//		curruent-=1*24*60*60*1000;
//		Date da = new Date(curruent);
		BigDecimal zero = new BigDecimal("0");
		for(ShopBalance shopBalance:shopBalances) {
			Balance balanceS = new Balance();
			Shop shop = new Shop();
			shop.setShopCode(shopBalance.getShopId());
			balanceS.setShop(shop);
			balanceS.setState(null);
			balanceS.setCreTime(begian);
			Balance balanceE = new Balance();
			balanceE.setCreTime(end);
			List<Balance> balances = this.findAll(balanceS,balanceE);
			BigDecimal incomeAmount = new BigDecimal("0");
			BigDecimal backAmount = new BigDecimal("0");
			
			for(Balance balance:balances) {
				if(Balance.transactionType_IN==balance.getTransactionType()) {
					incomeAmount = incomeAmount.add(new BigDecimal(balance.getAmount()));
				}
				if(Balance.transactionType_BACK==balance.getTransactionType()) {
					backAmount = backAmount.add(new BigDecimal(balance.getAmount()));
				}		
			}
			ShopBalance saveShopBalance = new ShopBalance();
			 
			if(incomeAmount.compareTo(zero)>=0 && backAmount.compareTo(zero)>=0) {
				saveShopBalance.setShopId(shopBalance.getShopId());
				saveShopBalance.setIncomeAmount(incomeAmount.toString());
				saveShopBalance.setBackAmount(backAmount.toString());
				rest.add(saveShopBalance);
			}
			
			
		}
		
		
		return rest;
		
	}
	
	/**
	 * 检查是否已经统计某一天收入
	 * @return true：已经存在 
	 * false：不存在
	 */
	private boolean checkIncomeIsSaved(Shop shop,Date be) {
		Assert.notNull(shop, "shop is not null");	
		PartnerIncome partnerIncome  = new PartnerIncome();
		partnerIncome.setShop(shop);	
		List<PartnerIncome> list = partnerIncomeService.findAll(partnerIncome);
		if(list!=null && list.size()>0) {
			for(PartnerIncome income:list) {
				if(income.getLogTime().getTime()==be.getTime()) {
					return true;
				}
			}
			return false;
		}else {
			return false;
		}
		
		
	}
	
	
	public String withdraw(Partner partner) {
		if(partner==null) {
			return null;
		}
		return null;
		
	}
	


}
