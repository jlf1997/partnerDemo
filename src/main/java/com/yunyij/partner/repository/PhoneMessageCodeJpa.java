package com.yunyij.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yunyij.partner.model.PhoneMessageCode;

public interface PhoneMessageCodeJpa extends JpaRepository<PhoneMessageCode, Long>,JpaSpecificationExecutor<PhoneMessageCode>{

}
