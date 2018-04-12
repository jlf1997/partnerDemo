package com.yunyij.partner.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.jlf1997.spring_boot_sdk.response.ResponseObject;
import com.yunyij.partner.config.JwtTokenFactory;
import com.yunyij.partner.constant.AuditState;
import com.yunyij.partner.model.Area;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.service.AreaService;
import com.yunyij.partner.service.LoginService;
import com.yunyij.partner.service.PartnerService;
import com.yunyij.partner.service.PhoneMessageCodeService;
import com.yunyij.partner.utils.IpUtil;
import com.yunyij.partner.utils.RandomString;
import com.yunyij.partner.vo.PartnerModeAndViewTranslation;
import com.yunyij.partner.vo.PartnerVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("users")
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private PhoneMessageCodeService phoneMessageCodeService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private JwtTokenFactory jwtTokenFactory;
	

	
	/**
	 * @description 用户登录 需要确保用户审核状态通过,且未被禁用
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "用户登录", notes = "返回jwtToken")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseObject<PartnerVO> login(
			@ApiParam(name = "phone",value="用户电话号码") @RequestParam  String phone,
			@ApiParam(name = "password",value="登陆密码") @RequestParam  String password) {
	
		Partner partner = partnerService.findByPhone(phone);
		
		String res = loginService.login(partner, password);
		if(res==null) {
			PartnerModeAndViewTranslation tra = new PartnerModeAndViewTranslation(areaService);
			
			Map<String, Object> map = new HashMap<>();
			map.put("partnerId", partner.getPartnerId());
			// 生成token
			String token = jwtTokenFactory.createJavaWebToken(map);
			PartnerVO vo = tra.modelToView(partner, PartnerVO.class);
			vo.setToken(token);
			return new ResponseObject<PartnerVO>().success(vo);
		}
		return new ResponseObject<PartnerVO>().failure(res);
	}
	
	
	/**
	 * 修改密码
	 *
	 * @param phone
	 * @param password
	 * @param newPassword
	 * @return
	 */
	@ApiOperation(value = "修改密码", notes = "")
	@RequestMapping(value = "/updateUserPassword", method = RequestMethod.PUT)
	public ResponseObject<String> updateUserPassword(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "password",value="用户旧密码") @RequestParam(value = "password") String password,
			@ApiParam(name = "newPassword",value="新密码") @RequestParam(value = "newPassword") String newPassword,
			@ApiParam(name = "affirmNewPassword",value="重复新密码") @RequestParam(value = "affirmNewPassword") String affirmNewPassword) {		
		Partner partner = partnerService.findOne(partnerId);
		if(partner==null) {
			return new ResponseObject<String>().failure("手机号不存在");
		}
		if(partner.getDisable()) {
			return new ResponseObject<String>().failure("禁用用户无法修改密码");
		}
		if(!AuditState.AGREE.equals(partner.getStatus())) {
			return new ResponseObject<String>().failure("用户正在审核中，无法修改密码");
		}
		
		String res = loginService.updateUserPassword(partner,password,newPassword,affirmNewPassword);
		if(res==null) {
			return new ResponseObject<String>().success();
		}
	
		return new ResponseObject<String>().failure(res);
	} 
	
	
	/**
	 * 修改密码
	 *
	 * @param phone
	 * @param password
	 * @param newPassword
	 * @return
	 */
	@ApiOperation(value = "忘记密码", notes = "")
	@RequestMapping(value = "/forgetPassword/{phone}", method = RequestMethod.PUT)
	public ResponseObject<String> forgetPassword(
			@ApiParam(name = "phone",value="用户电话号码",required=true) @PathVariable(value="phone",required=true)  String phone,
			@ApiParam(name = "newPassword",value="设置的新密码",required=true) @RequestParam(value = "newPassword",required=true) String newPassword,
			@ApiParam(name = "messageCode",value="短信验证码",required=true) @RequestParam(value = "messageCode",required=true) String messageCode
			
			) {		
		Partner partner = partnerService.findByPhone(phone);		
		if(partner==null) {
			return new ResponseObject<String>().failure("手机号不存在");
		}
		if(partner.getDisable()) {
			return new ResponseObject<String>().failure("禁用用户无法修改密码");
		}
		if(!phoneMessageCodeService.checkMessageCode(phone, messageCode)) {
			return new ResponseObject<String>().failure("验证码错误");
		}
		String res = loginService.forgetPassword(partner, newPassword);
		if(res==null) {
			return new ResponseObject<String>().success();
		}
		return new ResponseObject<String>().failure(res);
	} 
	
	
	@ApiOperation(value = "上传身份证图片 ", notes = "返回身份证图片上传后的地址")
	@RequestMapping(value = "/uploadIdCardsImage", method = RequestMethod.POST,
	consumes= "multipart/*",headers= {"content-type=multipart/form-data"})
	public ResponseObject<String> uploadIdCardsImage(
			MultipartHttpServletRequest request
		) throws IOException, Exception{
		
		List<MultipartFile> fileList = request.getFiles("files");
		
		
		String str = loginService.uploadFiles("idCards/", fileList);
		
		return new ResponseObject<String>().success(str);
	}
	
	/**
	 * 注册 保存用户资料
	 * @param address
	 * @param phone
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	@ApiOperation(value = "用户注册 ", notes = "保存用户资料")
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ResponseObject<PartnerVO> regist(
			@ApiParam(name = "idCardsNum",value="身份证号码",required=true) @RequestParam(value = "idCardsNum")  String idCardsNum,
			@ApiParam(name = "idCardsImg",value="身份证图片存储地址",required=true) @RequestParam(value = "idCardsImg")  String idCardsImg,
			@ApiParam(name = "address",value="详细地址",required=true) @RequestParam(value = "address")  String address,
			@ApiParam(name = "cityCode",value="城市编码",required=true) @RequestParam(value = "cityCode") Long cityCode,
			@ApiParam(name = "phone",value="用户电话号码",required=true)@RequestParam(value = "phone") String phone,
			@ApiParam(name = "refereeCode",value="推荐人编号",required=false)@RequestParam(value = "refereeCode",required=false) Long refereeCode
			){
		
		Partner partner = new Partner();
		//总公司
		partner.setBranchId(null);
		//未禁用
		partner.setDisable(false);
		partner.setAddress(address);
		partner.setPhone(phone);
		partner.setRefereeCode(refereeCode);
		partner.setIdCardsImg(idCardsImg);
		partner.setIdCardsNum(idCardsNum);
		Area area = areaService.findById(cityCode);
		if(area==null){
			return new ResponseObject<PartnerVO>().failure("city code 错误");
		}
		if(area.getLevel()!=3) {
			return new ResponseObject<PartnerVO>().failure("选择城市必须到县");
		}
		partner.setCityCode(cityCode);
		
		partner.setSaltFigure(RandomString.generateString(6));
		partner.setLoginPwd(loginService.md5PassWord("123456", partner.getSaltFigure()));
		//审核中
		partner.setStatus(AuditState.SUBMIT);
		String res = loginService.regist(partner);
		if(res==null) {
			PartnerModeAndViewTranslation tra = new PartnerModeAndViewTranslation(areaService);
			return new ResponseObject<PartnerVO>().success(tra.modelToView(partner, PartnerVO.class));
		}
		return new ResponseObject<PartnerVO>().failure(res);
	
	}
	
	/**
	 * 发送验证码到绑定号码
	 * 
	 * @param phone
	 * @return
	 */
	@ApiOperation(value = "发送验证码到绑定号码 ", notes = "")
	@RequestMapping(value = "/sendMessageCode", method = RequestMethod.GET)
	public ResponseObject<String> sendMessageCode(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			HttpServletRequest request
			) {
		Partner partner = partnerService.findOne(partnerId);
		String res = partnerService.checkPartnerDisable(partner);
		if(res!=null) {
			return new ResponseObject<String>().failure(res);
		}
		String phone = partner.getPhone();
		
		//验证号码
		String ip = IpUtil.getIpAddress(request);
		String str = loginService.sendMessageCode(phone, ip);
		if(str==null) {
			return new ResponseObject<String>().success();
		}
		return new ResponseObject<String>().failure(str);
		
		
		
	}

	/**
	 * 
	 * 注册获取验证码
	 * @param phone	 
	 * @return
	 */
	@ApiOperation(value = "注册获取验证码 ", notes = "")
	@RequestMapping(value = "/sendMessageCode/{phone}", method = RequestMethod.POST)
	public ResponseObject<String> registSendMessageCode(
			@ApiParam(name = "phone",value="合伙人注册号码") @PathVariable(value = "phone") String phone,
			HttpServletRequest request
			) {
		//验证号码
		String ip = IpUtil.getIpAddress(request);
		String str = loginService.sendMessageCode(phone, ip);
		if(str==null) {
			return new ResponseObject<String>().success();
		}
		return new ResponseObject<String>().failure(str);
		
	}
	
	/**
	 * 变更绑定号码
	 * @param partnerId
	 * @return
	 */
	@ApiOperation(value = "变更绑定号码 ", notes = "")
	@RequestMapping(value = "/changePhone", method = RequestMethod.POST)
	public ResponseObject<String> changePhone(
			@ApiParam(name = "partnerId",value="合伙人id",required=true) @RequestHeader(value="partnerId",required=true)Long partnerId,
			@ApiParam(name = "phone",value="合伙人新号码") @RequestParam(value = "phone") String phone,
			@ApiParam(name = "messageCode",value="短信验证码") @RequestParam(value = "messageCode") String messageCode
			) {
		Partner partner = partnerService.findOne(partnerId);
		if(partner==null) {
			return new ResponseObject<String>().failure("手机号不存在");
		}
		if(phoneMessageCodeService.checkMessageCode(partner.getPhone(), messageCode)){
			
			if(partner.getDisable()) {
				return new ResponseObject<String>().failure("禁用用户无法修改密码");
			}
			partner.setPhone(phone);
			partnerService.save(partner);
			return new ResponseObject<String>().success("修改成功");
		}
		return new ResponseObject<String>().failure("验证码错误");
	}
	
	
	/**
	 * 验证短信验证码
	 * @return
	 */
	@ApiOperation(value = "验证短信验证码 ", notes = "验证短信验证码")
	@RequestMapping(value = "/checkMessageCode", method = RequestMethod.GET)
	public ResponseObject<String> checkMessageCode(
			@ApiParam(name = "phone",value="合伙人号码") @RequestParam(value = "phone") String phone,
			@ApiParam(name = "messageCode",value="短信验证码") @RequestParam(value = "messageCode") String messageCode
			){
		
		if(phoneMessageCodeService.checkMessageCode(phone, messageCode)){
			return new ResponseObject<String>().success();
		}else {
			return new ResponseObject<String>().failure();
		}
		
		
	}

	
	
}
