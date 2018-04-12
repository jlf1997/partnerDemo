package com.yunyij.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yunyij.partner.model.BankCard;

public interface BankCardJpa extends JpaRepository<BankCard, Long>,JpaSpecificationExecutor<BankCard>{

}
