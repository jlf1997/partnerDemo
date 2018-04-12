package com.yunyij.partner.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.jlf1997.spring_boot_sdk.response.ResponseObject;
import com.github.jlf1997.spring_boot_sdk.service.FindBase;
import com.yunyij.partner.constant.AuditState;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.model.PhoneMessageCode;
import com.yunyij.partner.remote.message.MessageCodeClient;
import com.yunyij.partner.remote.message.vo.MessageCode;
import com.yunyij.partner.repository.PartnerRepository;
import com.yunyij.partner.utils.IdGener;
import com.yunyij.partner.utils.MD5Util;
import com.yunyij.partner.utils.PhoneRegular;
import com.yunyij.partner.utils.RandomCharacter;
import com.yunyij.partner.utils.RandomString;

import fileUpload.service.obs.ObsUploadFileManger;


@Service("loginService")
public class LoginService extends FindBase<Partner, Long>{

	@Autowired
	private PartnerRepository repository;
	
	@Autowired
	private PhoneMessageCodeService phoneMessageCodeService;
	
	@Autowired
	private MessageCodeClient messageCodeClient;

	
	public String sendMessageCode(String phone,String ip) {
		if(PhoneRegular.phoneVerify(phone)) {
			
			String checkCouldSend = phoneMessageCodeService.checkCouldSend(phone, ip);
			if(checkCouldSend!=null) {
				return checkCouldSend;
			}
			//发送短信
			MessageCode code = new MessageCode();
			// 生成随机6位验证码
			String messageCode = RandomCharacter.getMessageCode();
			// 生成消息内容
			String messageCodeResult = "您的注册验证码是" + messageCode + ",如非本人请勿操作,请不要把验证码泄漏给其他人";
			
			code.setPhone(phone);
			code.setMessage(messageCodeResult);
			
			PhoneMessageCode phoneMessageCode = new PhoneMessageCode();
			phoneMessageCode.setCode(messageCode);
			phoneMessageCode.setIp(ip);
			phoneMessageCode.setPhone(phone);
			
			// 远程调用消息服务
			ResponseObject urlResult = null;
			try {
				 urlResult = messageCodeClient.sendMessage(code);
				 phoneMessageCodeService.save(phoneMessageCode);
			}catch(Exception e) {
				return "远程连接错误";
			}
			if(urlResult!=null && !urlResult.isSuccess()) {
				return urlResult.getMsg();
			}
			
			return null;
		}else {
			return "手机号码格式错误";
		}
	}
	
	/**
	 * 合伙人注册
	 * @return
	 */
	public String regist(Partner partner) {
		partner.setPartnerId(IdGener.getInstance().getCodeId());
		//TODO 判断电话号码是否已注册
		
		Partner partnerRes = save(partner);
		
		if(partnerRes==null) {
			return "注册失败";
		}
		return null;
		
	}
	
	
	/**
	 * 忘记密码：修改密码
	 * @param partner
	 * @param newPassword
	 * @return
	 */
	public String forgetPassword(Partner partner,String newPassword) {
		
		partner.setSaltFigure(RandomString.generateString(6));
		newPassword = md5PassWord(newPassword,partner.getSaltFigure());
		partner.setLoginPwd(newPassword);
		
		Partner partnerRes = this.save(partner);
		if(partnerRes==null) {
			return "修改密码失败";
		}
		return null;
		
	}
	
	/**
	 * 设置密码
	 * @param partner
	 * @param password
	 * @return
	 */
	public String updateUserPassword(Partner partner,String password,String newPassword, String affirmNewPassword) {
		//TODO 校验旧密码
		String res = checkPassword(partner,password);
		if(res!=null) {
			return res;
		}
		if(newPassword==null || affirmNewPassword==null) {
			return "密码为空";
		}
		if(!newPassword.equals(affirmNewPassword)) {
			return "两次密码不一致";
		}
		
		//设置新密码
		partner.setSaltFigure(RandomString.generateString(6));
		newPassword = md5PassWord(newPassword,partner.getSaltFigure());
		partner.setLoginPwd(newPassword);
		
		Partner partnerRes = this.save(partner);
		if(partnerRes==null) {
			return "修改密码失败";
		}
		return null;
	}
	
	/**
	 * 上传文件到华为服务器
	 * @param path 文件路径
	 * @param request 
	 * @return 文件地址
	 * @throws IOException
	 * @throws Exception
	 */
	public String uploadFiles(String path,List<MultipartFile> fileList) throws IOException, Exception {
//		Iterator<String> fileNameIterator = request.getFileNames();
//		List<MultipartFile> fileList = Arrays.asList(files);
		ObsUploadFileManger obs = new ObsUploadFileManger("partner-info");
		String str = "";
		ArrayList<String> pathList = new ArrayList<>();

		for(MultipartFile uploadFile:fileList){
			str = obs.upload(path, uploadFile.getOriginalFilename(),uploadFile.getInputStream());
			str = "http://" + str;
			pathList.add(str);
		}
		
		return StringUtils.join(pathList.toArray(),",");
		
	}

	
	
	
	/**
	 * 对密码进行MD5编码
	 * @param password
	 * @return
	 */
	public String md5PassWord(String password,String salt) {
		return MD5Util.getMD5(salt+password);
	}
	
	
	/***
	 * 登陆
	 * @param p
	 * @param password
	 * @return
	 */
	public String login(Partner p, String password) {	
		String check =  checkPassword(p,password);
		if(check!=null) {
			return check;
		}
		if(!AuditState.AGREE.equals(p.getStatus())) {
			return "用户正在审核中";
		}
		if(p.getDisable()) {
			return "用户已被禁用";
		}
		return null;
	}
	
	/**
	 * 验证密码
	 * @param p
	 * @param password
	 * @return
	 */
	private String checkPassword(Partner p, String password) {
		if(p==null) {
			return "用户名或密码错误";
		}
		password = md5PassWord(password,p.getSaltFigure());
		
		if(!password.equals(p.getLoginPwd())) {
			return "用户名或密码错误";
		}
		
		return null;
	}
	
	
	
	

	@Override
	public void addWhere(Partner[] arg0, List<Predicate> arg1, Root<Partner> arg2, CriteriaQuery<?> arg3,
			CriteriaBuilder arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JpaRepository<Partner, Long> jpa() {
		// TODO Auto-generated method stub
		return repository;
	}

	@Override
	public void setSelect(Partner arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JpaSpecificationExecutor<Partner> specjpa() {
		// TODO Auto-generated method stub
		return repository;
	}

	
}
