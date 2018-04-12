package com.yunyij.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yunyij.partner.model.Shop;

public interface ShopJpa extends JpaRepository<Shop, Long>,JpaSpecificationExecutor<Shop>{

}
