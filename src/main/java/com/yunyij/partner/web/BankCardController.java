package com.yunyij.partner.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jlf1997.spring_boot_sdk.response.ResponseObject;
import com.yunyij.partner.model.BankCard;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.service.BankCardService;
import com.yunyij.partner.service.PartnerService;
import com.yunyij.partner.utils.IdGener;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("bankCard")
public class BankCardController {
	
	@Autowired
	private BankCardService bankCardService;
	
	@Autowired
	private PartnerService partnerService;

	/**
	 * 新增银行卡
	 * @param partnerId
	 * @return
	 */
	@ApiOperation(value = "新增银行卡", notes = "")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseObject<String> save(			
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "name",value="姓名",required=true) @RequestParam(value="name",required=true)String name,
			@ApiParam(name = "phone",value="银行预留电话",required=true) @RequestParam(value="phone",required=true)String phone,
			@ApiParam(name = "cardNum",value="银行卡号",required=true) @RequestParam(value="cardNum",required=true)String cardNum,
			@ApiParam(name = "bankPro",value="开户省份",required=true) @RequestParam(value="bankPro",required=true)String bankPro,
			@ApiParam(name = "bankCity",value="开户城市",required=true) @RequestParam(value="bankCity",required=true)String bankCity,
			@ApiParam(name = "region",value="开户区",required=true) @RequestParam(value="region",required=true)String region,
			@ApiParam(name = "branch",value="开户支行",required=true) @RequestParam(value="branch",required=true)String branch,
			@ApiParam(name = "bankType",value="开户银行",required=true) @RequestParam(value="bankType",required=true)String bankType
			){
		
		Partner partner = partnerService.findOne(partnerId);
		if(partner!=null) {
			BankCard bankcard = new BankCard();
			bankcard.setBankCity(bankCity);
			bankcard.setBankType(bankType);
			bankcard.setBranch(branch);
			bankcard.setId(IdGener.getInstance().getId());
			bankcard.setBankPro(bankPro);
			bankcard.setRegion(region);
			bankcard.setUsername(name);
			bankcard.setPartner(partner);
			bankcard.setCardNum(cardNum);
			bankCardService.save(bankcard);
			return new ResponseObject<String>().success();
		}
		return new ResponseObject<String>().failure("partnerid 错误");
	}
	
	
	
	/**
	 * 移除银行卡
	 * @param partnerId
	 * @return
	 */
	@ApiOperation(value = "移除银行卡", notes = "")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public ResponseObject<BankCard> del(			
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId
			){
		Partner partner = partnerService.findOne(partnerId);
		if(partner!=null) {
			BankCard t = new BankCard();
			t.setPartner(partner);
			List<BankCard> list = bankCardService.findAll(t);
			bankCardService.delete(list.get(0));
		}
		
		return new ResponseObject<BankCard>().failure("暂未实现");
		
	} 
	
	
	/**
	 * 查询绑定的银行卡
	 * @param partnerId
	 * @return
	 */
	@ApiOperation(value = "查询绑定的银行卡", notes = "")
	@RequestMapping(value = "/findBy", method = RequestMethod.GET)
	public ResponseObject<BankCard> findBy(			
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId
			){
		Partner partner = partnerService.findOne(partnerId);
		if(partner!=null) {
			BankCard t = new BankCard();
			t.setPartner(partner);
			List<BankCard> list = bankCardService.findAll(t);
			return new ResponseObject<BankCard>().success(list);
		}
		return new ResponseObject<BankCard>().failure("partnerid 错误");
	
		
	}
	
}
