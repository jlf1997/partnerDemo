package com.yunyij.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yunyij.partner.model.Balance;

public interface BalanceJpa extends JpaRepository<Balance, Long>,JpaSpecificationExecutor<Balance>{

}
