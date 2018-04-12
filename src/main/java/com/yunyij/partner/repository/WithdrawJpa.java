package com.yunyij.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yunyij.partner.model.Withdraw;

public interface WithdrawJpa extends JpaRepository<Withdraw, Long>,JpaSpecificationExecutor<Withdraw>{

}
