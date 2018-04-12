package com.yunyij.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.yunyij.partner.model.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long>,JpaSpecificationExecutor<Partner>{

	/**
	 * 查询手机号是否已经注册
	 * @param phone
	 * @param email
	 * @return
	 */
	Long countByPhone(String phone);
	
//	/**
//	 * 根据合伙人ID获取合伙人信息
//	 * @param code
//	 * @return
//	 */
//	Partner getByIdAndDeletedTimeIsNull(Long userId);
//	
//	/**
//	 * 根据合伙人电话获取合伙人信息
//	 * @param code
//	 * @return
//	 */
//	Partner getByPhoneAndDeletedTimeIsNull(String phone);
//	
//	/**
//	 * 根据合伙人编号获取合伙人信息
//	 * @param code
//	 * @return
//	 */
//	Partner getByCodeAndDeletedTimeIsNull(String code);
	
	

}
