package com.yunyij.partner.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jlf1997.spring_boot_sdk.response.ResponseObject;
import com.github.jlf1997.spring_boot_sdk.response.ResponsePage;
import com.github.jlf1997.spring_boot_sdk.service.SpringDataJpaFinder;
import com.github.jlf1997.spring_boot_sdk.util.SpringDataJpaUtils;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.model.PartnerIncome;
import com.yunyij.partner.model.Shop;
import com.yunyij.partner.model.Withdraw;
import com.yunyij.partner.service.BalanceService;
import com.yunyij.partner.service.PartnerIncomeService;
import com.yunyij.partner.service.PartnerService;
import com.yunyij.partner.service.WithdrawService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("income")
public class BalanceController {
	
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private PartnerIncomeService partnerIncomeService;
	@Autowired
	private WithdrawService withdrawService;
	
	@ApiOperation(value = "获取所有收入金额", notes = "")
	@RequestMapping(value = "/getAllAmount", method = RequestMethod.GET)
	public ResponseObject<String> getAllAmount(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId
			){
		Partner partner = new Partner();
		partner.setPartnerId(partnerId);
		Partner obj = partnerService.find(partner);
		if(obj!=null) {
			String allAmout = obj.getBalanceAll();
			return new ResponseObject<String>().success(allAmout);
		}
		return new ResponseObject<String>().failure();
		
		
	}
	
	@ApiOperation(value = "获取可提现金额", notes = "")
	@RequestMapping(value = "/getAvailable", method = RequestMethod.GET)
	public ResponseObject<String> getAvailable(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId
			){
		Partner partner = new Partner();
		partner.setPartnerId(partnerId);
		Partner obj = partnerService.find(partner);
		if(obj!=null) {
//			String avialableAmout = balanceService.getBalance(obj);
			String avialableAmout = obj.getBalance();
			return new ResponseObject<String>().success(avialableAmout);
		}
		return new ResponseObject<String>().failure();
		
	}
	
	@ApiOperation(value = "获取暂时冻结金额", notes = "")
	@RequestMapping(value = "/getNotAvailable", method = RequestMethod.GET)
	public ResponseObject<String> getNotAvailable(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId
			){
		Partner partner = new Partner();
		partner.setPartnerId(partnerId);
		Partner obj = partnerService.find(partner);
		if(obj!=null) {
			BigDecimal balanceAll = new BigDecimal("0");
			if(partner.getBalanceAll()!=null) {
				balanceAll = new BigDecimal(partner.getBalanceAll());
			}
			BigDecimal balance = new BigDecimal(partner.getBalanceAll()==null?"0":partner.getBalanceAll());
//			if(partner.getBalanceAll()!=null) {
//				balance = new BigDecimal(balanceService.getBalance(obj));
//			}
			String notAvailableAmount = balanceAll.subtract(balance).toString();
			return new ResponseObject<String>().success(notAvailableAmount);
		}
		return new ResponseObject<String>().failure();
		
	}
	
	@ApiOperation(value = "获取所有收入记录", notes = "默认按照创建时间排序")
	@RequestMapping(value = "/findBy", method = RequestMethod.GET)
	public ResponseObject<PartnerIncome> findBy(@ApiParam(name = "address",value="用户联系地址,模糊查询") @RequestParam(value = "address", required = false) String address,
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "phone",value="联系电话") @RequestParam(value = "phone", required = false) String phone,
			@ApiParam(name = "logTimeBegin",value="收入时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "logTimeBegin", required = false) Long logTimeBegin,
			@ApiParam(name = "logTimeEnd",value="收入时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "logTimeEnd", required = false) Long logTimeEnd,
			@ApiParam(name = "creTimeBegin",value="记录创建时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeBegin", required = false) Long creTimeBegin,
			@ApiParam(name = "creTimeEnd",value="记录创建时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeEnd", required = false) Long creTimeEnd,
			@ApiParam(name = "updTimeBegin",value="记录最近修改时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeBegin", required = false) Long updTimeBegin,
			@ApiParam(name = "updTimeEnd",value="记录最近修改时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeEnd", required = false) Long updTimeEnd,			
			@ApiParam(name = "orderBy",value="排序字段") @RequestParam(value = "orderBy", defaultValue = "creTime",required = false) String sortStr,
			@ApiParam(name = "orderType",value="排序类型") @RequestParam(value = "orderType", defaultValue = "DESC",required = false) Direction direction,
			@ApiParam(name = "pageSize",value="每页大小") @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize,
			@ApiParam(name = "pageIndex",value="页序号，从0开始") @RequestParam(value = "pageIndex", defaultValue = "0",required = false) Integer pageIndex){
		
			
			Partner partner = new Partner();
			partner.setPartnerId(partnerId);			
			Shop shop = new Shop();
			shop.setPartner(partner);
			PartnerIncome partnerIncome = new PartnerIncome();
			partnerIncome.setShop(shop);
			if(logTimeBegin!=null) {
				partnerIncome.setLogTime(new Date(logTimeBegin));
			}			
			PartnerIncome partnerIncomeEnd = new PartnerIncome();
			if(logTimeEnd!=null) {
				partnerIncomeEnd.setLogTime(new Date(logTimeEnd));
			}
		
			Page<PartnerIncome> pages = partnerIncomeService.findAllPage(sortStr,
					 direction,  pageSize,  pageIndex,
					 partnerIncome,partnerIncomeEnd);
			if(pages!=null) {
				return new ResponseObject<PartnerIncome>().success(new ResponsePage<>(pages, pageIndex));
			}
			return new ResponseObject<PartnerIncome>().failure("");
		
	}
	
	
	@ApiOperation(value = "提现", notes = "")
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public ResponseObject<String> withdraw(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "amount",value="提现金额") @RequestParam(value = "amount", required = true) String amount
			){
		BigDecimal am =null;
		try {
			 am = new BigDecimal(amount);			 
		}catch(Exception e) {
			return new ResponseObject<String>().failure("amount 参数错误");
		}
		
		if(am==null || am.compareTo(new BigDecimal("0"))<=0) {
			return new ResponseObject<String>().failure("提现金额必须大于0");
		}
		Partner partner = new Partner();
		partner.setPartnerId(partnerId);
		Partner obj = partnerService.find(partner);
		if(obj!=null) {
			String str = withdrawService.withdraw(obj,am);
			
			if(str==null) {
				return new ResponseObject<String>().success("提现申请提交成功");
			}else {
				return new ResponseObject<String>().failure("申请失败:"+str);
			}
			
			
		}
		return new ResponseObject<String>().failure("申请失败");
		
	}
	
//	@ApiOperation(value = "获取所有收入记录", notes = "默认按照创建时间排序")
//	@RequestMapping(value = "/findReportBy", method = RequestMethod.GET)
//	public ResponseObject<PartnerIncome> findReportBy(@ApiParam(name = "address",value="用户联系地址,模糊查询") @RequestParam(value = "address", required = false) String address,
//			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
//			@ApiParam(name = "phone",value="联系电话") @RequestParam(value = "phone", required = false) String phone,
//			@ApiParam(name = "logTimeBegin",value="收入时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "logTimeBegin", required = false) Long logTimeBegin,
//			@ApiParam(name = "logTimeEnd",value="收入时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "logTimeEnd", required = false) Long logTimeEnd,
//			@ApiParam(name = "creTimeBegin",value="记录创建时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeBegin", required = false) Long creTimeBegin,
//			@ApiParam(name = "creTimeEnd",value="记录创建时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeEnd", required = false) Long creTimeEnd,
//			@ApiParam(name = "updTimeBegin",value="记录最近修改时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeBegin", required = false) Long updTimeBegin,
//			@ApiParam(name = "updTimeEnd",value="记录最近修改时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeEnd", required = false) Long updTimeEnd,			
//			@ApiParam(name = "orderBy",value="排序字段") @RequestParam(value = "orderBy", defaultValue = "creTime",required = false) String sortStr,
//			@ApiParam(name = "orderType",value="排序类型") @RequestParam(value = "orderType", defaultValue = "DESC",required = false) Direction direction,
//			@ApiParam(name = "pageSize",value="每页大小") @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize,
//			@ApiParam(name = "pageIndex",value="页序号，从0开始") @RequestParam(value = "pageIndex", defaultValue = "0",required = false) Integer pageIndex){
//		
//			
//			Partner partner = new Partner();
//			partner.setPartnerId(partnerId);
//			Shop shop = new Shop();
//			shop.setPartner(partner);
//			PartnerIncome partnerIncome = new PartnerIncome();
//			partnerIncome.setShop(shop);
//			if(logTimeBegin!=null) {
//				partnerIncome.setLogTime(new Date(logTimeBegin));
//			}			
//			PartnerIncome partnerIncomeEnd = new PartnerIncome();
//			if(logTimeEnd!=null) {
//				partnerIncomeEnd.setLogTime(new Date(logTimeEnd));
//			}
//		
//			Page<PartnerIncome> pages = partnerIncomeService.findAllPage(new SpringDataJpaFinder<PartnerIncome>() {
//
//				@Override
//				public boolean where(List<Predicate> predicates, Root<PartnerIncome> root, CriteriaQuery<?> query,
//						CriteriaBuilder cb, PartnerIncome... t) {
//					// TODO Auto-generated method stub
//					query = query.multiselect(root.get("shop"),cb.sum(root.get("inAmount")));
//					query.groupBy(root.get("shop"));
//					//
////					SpringDataJpaUtils.where(predicates, root, query, cb, t);
//					return true;
//				}
//
//				
//				
//			},
//			sortStr,	 direction,  pageSize,  pageIndex,
//					 partnerIncome,partnerIncomeEnd);
//			if(pages!=null) {
//				return new ResponseObject<PartnerIncome>().success(new ResponsePage<>(pages, pageIndex));
//			}
//			return new ResponseObject<PartnerIncome>().failure("");
//		
//	}
	
	
	@ApiOperation(value = "获取提现记录", notes = "")
	@RequestMapping(value = "/getWithdrawPages", method = RequestMethod.GET)
	public ResponseObject<Withdraw> getWithdrawPages(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "creTimeBegin",value="记录创建时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeBegin", required = false) Long creTimeBegin,
			@ApiParam(name = "creTimeEnd",value="记录创建时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeEnd", required = false) Long creTimeEnd,
			@ApiParam(name = "updTimeBegin",value="记录最近修改时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeBegin", required = false) Long updTimeBegin,
			@ApiParam(name = "updTimeEnd",value="记录最近修改时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeEnd", required = false) Long updTimeEnd,			
			@ApiParam(name = "orderBy",value="排序字段") @RequestParam(value = "orderBy", defaultValue = "creTime",required = false) String sortStr,
			@ApiParam(name = "orderType",value="排序类型") @RequestParam(value = "orderType", defaultValue = "DESC",required = false) Direction direction,
			@ApiParam(name = "pageSize",value="每页大小") @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize,
			@ApiParam(name = "pageIndex",value="页序号，从0开始") @RequestParam(value = "pageIndex", defaultValue = "0",required = false) Integer pageIndex
			){
		Partner partner = new Partner();
		partner.setPartnerId(partnerId);
		Withdraw withdraw = new Withdraw(creTimeBegin,updTimeBegin);
		withdraw.setPartner(partner);
		Withdraw withdrawE = new Withdraw(creTimeBegin,updTimeBegin);
		Page<Withdraw> pages = withdrawService.findAllPage(sortStr,direction,pageSize,pageIndex, withdraw,withdrawE);
		
		return new ResponseObject<Withdraw>().success(new ResponsePage<>(pages,pageIndex));
		
		
	}
	
	
	
}
