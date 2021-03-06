package com.yunyij.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yunyij.partner.model.PartnerIncome;

public interface PartnerIncomeJpa extends JpaRepository<PartnerIncome, Long>,JpaSpecificationExecutor<PartnerIncome>{

}
