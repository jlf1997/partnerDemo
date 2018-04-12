package com.yunyij.partner.service;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.github.jlf1997.spring_boot_sdk.service.FindBase;
import com.yunyij.partner.model.PhoneMessageCode;
import com.yunyij.partner.repository.PhoneMessageCodeJpa;
import com.yunyij.partner.utils.TimeUtilV;


/**
 * 
 * @author Administrator
 *
 */
@Service("phoneMessageCodeService")
public class PhoneMessageCodeService extends FindBase<PhoneMessageCode,Long>{
	
	@Autowired
	private PhoneMessageCodeJpa phoneMessageCodeJpa;
	
	
	
	/**
	 * 检验是否可以在发送验证码
	 * @return
	 */
	public String checkCouldSend(String phone,String ip) {
		PhoneMessageCode pmc1 = new PhoneMessageCode();
//		pmc1.setPhone(phone);
		pmc1.setIp(ip);
		pmc1.setCreTime(TimeUtilV.getTodayStartTime());
		PhoneMessageCode pmc2 = new PhoneMessageCode();
		pmc2.setCreTime(TimeUtilV.getTodyEndTime());
		
		List<PhoneMessageCode> list = this.findAll("creTime",Direction.DESC,pmc1,pmc2);
		
		//当天同一ip发送小于3次
		if(list!=null && list.size()>=10) {
			return "今天发送已超过10次，请明天在试";
		}
		Long currentTime = new Date().getTime();
		if(list!=null && list.size()>0 && currentTime-list.get(0).getCreTime().getTime()<120*1000) {
			return "操作太频繁，请稍后重试";
		}
		return null;
	}
	
	public boolean checkMessageCode(String phone,String messageCode) {
		PhoneMessageCode pmc = new PhoneMessageCode();
		pmc.setPhone(phone);
		Long now = new Date().getTime();
		now-=5*60*1200;
		//验证码5分钟后失效
		pmc.setCreTime(new Date(now));
		List<PhoneMessageCode> list = this.findAll("creTime",Direction.DESC,pmc);
		if(messageCode!=null && list!=null && list.size()>0 && messageCode.equals(list.get(0).getCode())) {
			return true;
		}
		return false;
		
	}

	@Override
	public JpaSpecificationExecutor<PhoneMessageCode> specjpa() {
		// TODO Auto-generated method stub
		return phoneMessageCodeJpa;
	}

	@Override
	public JpaRepository<PhoneMessageCode, Long> jpa() {
		// TODO Auto-generated method stub
		return phoneMessageCodeJpa;
	}

	@Override
	public void addWhere(PhoneMessageCode[] t, List<Predicate> predicates, Root<PhoneMessageCode> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(PhoneMessageCode t) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
