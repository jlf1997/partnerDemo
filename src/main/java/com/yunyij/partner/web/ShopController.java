package com.yunyij.partner.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.jlf1997.spring_boot_sdk.response.ResponseObject;
import com.github.jlf1997.spring_boot_sdk.response.ResponsePage;
import com.yunyij.partner.constant.AuditState;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.model.Shop;
import com.yunyij.partner.service.LoginService;
import com.yunyij.partner.service.PartnerService;
import com.yunyij.partner.service.ShopService;
import com.yunyij.partner.utils.IdGener;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api(value="商店相关接口")
@RestController
@RequestMapping("shop")
public class ShopController {
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private PartnerService partnerService;
	
	@Autowired
	private ShopService shopService;
	
	@ApiOperation(value = "上传营业执照图片 ", notes = "返回图片上传后的地址")
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST,
	consumes= "multipart/*",headers= {"content-type=multipart/form-data"})
	public ResponseObject<String> uploadImage(
	
			MultipartHttpServletRequest request
		) throws IOException, Exception{
		
		String str = loginService.uploadFiles("businessLicense/", request.getFiles("files"));
		
		return new ResponseObject<String>().success(str);
	}
	
	
	@ApiOperation(value = "录入商店资料", notes = "")
	@RequestMapping(value = "/saveShop", method = RequestMethod.POST)
	public ResponseObject<Shop> saveShop(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "businessLicenseName",value="营业执照公司名",required=true) @RequestParam(value="businessLicenseName",required=true)String businessLicenseName,
			@ApiParam(name = "businessLicenseCode",value="营业执照编号",required=true) @RequestParam(value="businessLicenseCode",required=true)String businessLicenseCode,
			@ApiParam(name = "businessLicenseImg",value="营业执照图片",required=true) @RequestParam(value="businessLicenseImg",required=true)String businessLicenseImg,
			@ApiParam(name = "phone",value="联系电话",required=true) @RequestParam(value="phone",required=true)String phone,
			@ApiParam(name = "idCardNum",value="法人代表身份证号码",required=true) @RequestParam(value="idCardNum",required=true)String idCardNum,
			@ApiParam(name = "idCardName",value="法人代表名字",required=true) @RequestParam(value="idCardName",required=true)String idCardName,
			@ApiParam(name = "idCardImg",value="法人代表身份图片",required=true) @RequestParam(value="idCardImg",required=true)String idCardImg
			
			){
		
		Partner partner = partnerService.findOne(partnerId);
		if(partner==null) {
			return new ResponseObject<Shop>().failure("partnerId 错误"); 
		}		
		Shop shop = new Shop();
		shop.setPartner(partner);
		shop.setBusinessLicenseName(businessLicenseName);
		shop.setBusinessLicenseCode(businessLicenseCode);
		shop.setBusinessLicenseImg(businessLicenseImg);
		shop.setPhone(phone);
		shop.setShopCode(IdGener.getInstance().getCodeId());
		shop.setIdCardImg(idCardImg);
		shop.setIdCardNum(idCardNum);
		shop.setIdCardName(idCardName);
		shop.setAuditState(AuditState.SUBMIT);
		
		
		shop = shopService.save(shop);
		if(shop!=null) {
			return new ResponseObject<Shop>().success(shop);
		}
		return new ResponseObject<Shop>().failure();
		
	}
	
	@ApiOperation(value = "获取所有推荐商店信息", notes = "")
	@RequestMapping(value = "/getShop", method = RequestMethod.GET)
	public ResponseObject<Shop> getShop(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "creTimeBegin",value="记录创建时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeBegin", required = false) Long creTimeBegin,
			@ApiParam(name = "creTimeEnd",value="记录创建时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeEnd", required = false) Long creTimeEnd,
			@ApiParam(name = "updTimeBegin",value="记录最近修改时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeBegin", required = false) Long updTimeBegin,
			@ApiParam(name = "updTimeEnd",value="记录最近修改时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeEnd", required = false) Long updTimeEnd,			
			@ApiParam(name = "orderBy",value="排序字段") @RequestParam(value = "orderBy", defaultValue = "updTime",required = false) String sortStr,
			@ApiParam(name = "orderType",value="排序类型") @RequestParam(value = "orderType", defaultValue = "DESC",required = false) Direction direction,
			@ApiParam(name = "pageSize",value="每页大小") @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize,
			@ApiParam(name = "pageIndex",value="页序号，从0开始") @RequestParam(value = "pageIndex", defaultValue = "0",required = false) Integer pageIndex
			){
		
		Shop shop = new Shop(creTimeBegin,updTimeBegin);
		Partner partner = new Partner();
		partner.setPartnerId(partnerId);
		shop.setPartner(partner);
		Shop shop1 = new Shop(creTimeEnd,updTimeEnd);
		
		Page<Shop> pages = shopService.findAllPage(sortStr,direction,pageSize,pageIndex, shop,shop1);
		
		
		return new ResponseObject<Shop>().success(new ResponsePage<>(pages,pageIndex));
		
	}
	
	
	@ApiOperation(value = "获取审核中商店信息", notes = "")
	@RequestMapping(value = "/getShop/auditing", method = RequestMethod.GET)
	public ResponseObject<Shop> getShopAuditing(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "creTimeBegin",value="记录创建时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeBegin", required = false) Long creTimeBegin,
			@ApiParam(name = "creTimeEnd",value="记录创建时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeEnd", required = false) Long creTimeEnd,
			@ApiParam(name = "updTimeBegin",value="记录最近修改时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeBegin", required = false) Long updTimeBegin,
			@ApiParam(name = "updTimeEnd",value="记录最近修改时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeEnd", required = false) Long updTimeEnd,			
			@ApiParam(name = "orderBy",value="排序字段") @RequestParam(value = "orderBy", defaultValue = "updTime",required = false) String sortStr,
			@ApiParam(name = "orderType",value="排序类型") @RequestParam(value = "orderType", defaultValue = "DESC",required = false) Direction direction,
			@ApiParam(name = "pageSize",value="每页大小") @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize,
			@ApiParam(name = "pageIndex",value="页序号，从0开始") @RequestParam(value = "pageIndex", defaultValue = "0",required = false) Integer pageIndex
			){
		Shop shop = new Shop(creTimeBegin,updTimeBegin);
		Partner partner = new Partner();
		partner.setPartnerId(partnerId);
		shop.setPartner(partner);
		shop.setAuditState(AuditState.SUBMIT);
		Shop shop1 = new Shop(creTimeEnd,updTimeEnd);
	
		Page<Shop> pages = shopService.findAllPage(sortStr,direction,pageSize,pageIndex, shop,shop1);
		return new ResponseObject<Shop>().success(new ResponsePage<>(pages,pageIndex));
		
	}
	
	@ApiOperation(value = "获取审核通过商店信息", notes = "")
	@RequestMapping(value = "/getShop/audited", method = RequestMethod.GET)
	public ResponseObject<Shop> getShopAudited(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "creTimeBegin",value="记录创建时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeBegin", required = false) Long creTimeBegin,
			@ApiParam(name = "creTimeEnd",value="记录创建时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeEnd", required = false) Long creTimeEnd,
			@ApiParam(name = "updTimeBegin",value="记录最近修改时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeBegin", required = false) Long updTimeBegin,
			@ApiParam(name = "updTimeEnd",value="记录最近修改时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeEnd", required = false) Long updTimeEnd,			
			@ApiParam(name = "orderBy",value="排序字段") @RequestParam(value = "orderBy", defaultValue = "updTime",required = false) String sortStr,
			@ApiParam(name = "orderType",value="排序类型") @RequestParam(value = "orderType", defaultValue = "DESC",required = false) Direction direction,
			@ApiParam(name = "pageSize",value="每页大小") @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize,
			@ApiParam(name = "pageIndex",value="页序号，从0开始") @RequestParam(value = "pageIndex", defaultValue = "0",required = false) Integer pageIndex
			){
		Shop shop = new Shop(creTimeBegin,updTimeBegin);
		Partner partner = new Partner();
		partner.setPartnerId(partnerId);
		shop.setPartner(partner);
		shop.setAuditState(AuditState.AGREE);
		Shop shop1 = new Shop(creTimeEnd,updTimeEnd);
		
		Page<Shop> pages= shopService.findAllPage(sortStr,direction,pageSize,pageIndex, shop,shop1);
		return new ResponseObject<Shop>().success(new ResponsePage<>(pages,pageIndex));
		
	}
	
	@ApiOperation(value = "条件查询关联商店", notes = "")
	@RequestMapping(value = "/findBy", method = RequestMethod.GET)
	public ResponseObject<Shop> findBy(@ApiParam(name = "address",value="用户联系地址,模糊查询") @RequestParam(value = "address", required = false) String address,
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "businessLicenseName",value="营业执照公司名",required=false) @RequestParam(value="businessLicenseName",required=false)String businessLicenseName,
			@ApiParam(name = "creTimeBegin",value="记录创建时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeBegin", required = false) Long creTimeBegin,
			@ApiParam(name = "creTimeEnd",value="记录创建时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeEnd", required = false) Long creTimeEnd,
			@ApiParam(name = "updTimeBegin",value="记录最近修改时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeBegin", required = false) Long updTimeBegin,
			@ApiParam(name = "updTimeEnd",value="记录最近修改时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeEnd", required = false) Long updTimeEnd,			
			@ApiParam(name = "orderBy",value="排序字段") @RequestParam(value = "orderBy", defaultValue = "updTime",required = false) String sortStr,
			@ApiParam(name = "orderType",value="排序类型") @RequestParam(value = "orderType", defaultValue = "DESC",required = false) Direction direction,
			@ApiParam(name = "pageSize",value="每页大小") @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize,
			@ApiParam(name = "pageIndex",value="页序号，从0开始") @RequestParam(value = "pageIndex", defaultValue = "0",required = false) Integer pageIndex
			){
		
		Shop shop = new Shop(creTimeBegin,updTimeBegin);
		Partner partner = new Partner();
		partner.setPartnerId(partnerId);
		shop.setPartner(partner);
		shop.setBusinessLicenseName(businessLicenseName);		
		Shop shop1 = new Shop(creTimeEnd,updTimeEnd);
		Page<Shop> pages= shopService.findAllPage(sortStr,direction,pageSize,pageIndex, shop,shop1);
		return new ResponseObject<Shop>().success(new ResponsePage<>(pages,pageIndex));
		
	}
	
	

}
