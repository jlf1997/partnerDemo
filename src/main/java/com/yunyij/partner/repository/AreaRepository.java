package com.yunyij.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yunyij.partner.model.Area;

public interface AreaRepository extends JpaRepository<Area, Long>,JpaSpecificationExecutor<Area>{

}
