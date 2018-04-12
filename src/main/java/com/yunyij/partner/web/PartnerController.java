package com.yunyij.partner.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.github.jlf1997.spring_boot_sdk.vo_translation.ModeAndViewTranslation;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.service.AreaService;
import com.yunyij.partner.service.PartnerService;
import com.yunyij.partner.vo.PartnerModeAndViewTranslation;
import com.yunyij.partner.vo.PartnerVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("finder")
public class PartnerController {
	
	private static final Logger log = LoggerFactory.getLogger(PartnerController.class);
	
	@Autowired
	private PartnerService partnerService;
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * @description 根据合伙人编号查询合伙人
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据合伙人编号查询合伙人", notes = "")
	@RequestMapping(value = "/getPartnerById", method = RequestMethod.GET)
	public ResponseObject<PartnerVO> getPartnerById(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId
			) {
		
		Partner p = new Partner();
		p.setPartnerId(partnerId);
		Partner dto = partnerService.find(p);
		ModeAndViewTranslation<Partner,PartnerVO> tr = new ModeAndViewTranslation<>();
		if(dto==null) {
			return new ResponseObject<PartnerVO>().failure("没有对应的用户信息");
		}else {
			return new ResponseObject<PartnerVO>().success(tr.modelToView(dto, PartnerVO.class));
		}
	}
	
	
	
	
	
	
	@ApiOperation(value = "查询所有合伙人列表", notes = "测试接口，不实际使用")
	@RequestMapping(value = "/findAllList", method = RequestMethod.GET)
	public ResponseObject<PartnerVO> findAllList() {
		List<Partner> dto = partnerService.findAll();
		ModeAndViewTranslation<Partner,PartnerVO> tr = new ModeAndViewTranslation<>();
		if(dto==null) {
			return new ResponseObject<PartnerVO>().failure("没有对应的用户信息");
		}else {
			return new ResponseObject<PartnerVO>().success(tr.modelListToViewList(dto, PartnerVO.class));
		}
	}
	
	
	@ApiOperation(value = "条件查询合伙人", notes = "")
	@RequestMapping(value = "/findBy", method = RequestMethod.GET)
	public ResponseObject<PartnerVO> findBy(			
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "address",value="用户联系地址,模糊查询") @RequestParam(value = "address", required = false) String address,
			@ApiParam(name = "phone",value="用户电话号码") @RequestParam(value = "phone", required = false) String phone,
			@ApiParam(name = "branchId",value="所属公司id") @RequestParam(value = "branchId",required = false) Long branchId,
			@ApiParam(name = "refereeCode",value="推荐人id") @RequestParam(value = "refereeCode",required = false) Long refereeCode,
			@ApiParam(name = "creTimeBegin",value="记录创建时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeBegin", required = false) Long creTimeBegin,
			@ApiParam(name = "creTimeEnd",value="记录创建时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "creTimeEnd", required = false) Long creTimeEnd,
			@ApiParam(name = "updTimeBegin",value="记录最近修改时间开始（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeBegin", required = false) Long updTimeBegin,
			@ApiParam(name = "updTimeEnd",value="记录最近修改时间结束（unix时间戳 精确到毫秒）",example="1520908169000") @RequestParam(value = "updTimeEnd", required = false) Long updTimeEnd,			
			@ApiParam(name = "orderBy",value="排序字段") @RequestParam(value = "orderBy", defaultValue = "updTime",required = false) String sortStr,
			@ApiParam(name = "orderType",value="排序类型") @RequestParam(value = "orderType", defaultValue = "DESC",required = false) Direction direction,
			@ApiParam(name = "pageSize",value="每页大小") @RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize,
			@ApiParam(name = "pageIndex",value="页序号，从0开始") @RequestParam(value = "pageIndex", defaultValue = "0",required = false) Integer pageIndex){
		Partner partner1 = new Partner(creTimeBegin,updTimeBegin);	
		partner1.setPartnerId(partnerId);
		Partner partner2 = new Partner(creTimeEnd,updTimeEnd);
		Page<Partner>  pages = partnerService.findAllPage(sortStr,direction,pageSize,pageIndex,partner1,partner2);
		ResponseObject<PartnerVO> rest=  new ResponseObject<>();	
		ResponsePage<PartnerVO> rr = new ResponsePage<>(new PartnerModeAndViewTranslation(areaService),pages,pageIndex,PartnerVO.class);
		return rest.success(rr);	
	}

	

	

}
