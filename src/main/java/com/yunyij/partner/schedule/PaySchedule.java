package com.yunyij.partner.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yunyij.partner.model.Balance;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.service.BalanceService;
import com.yunyij.partner.service.PartnerService;
import com.yunyij.partner.service.ShopService;


@Component
public class PaySchedule {
	private static final Logger log = LoggerFactory.getLogger(PaySchedule.class);

	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private ShopService shopService;
	

	@Autowired
	private PartnerService partnerService;

	
	@Autowired
	 private KafkaTemplate kafkaTemplate;
	@Scheduled(cron = "0 0 2 * * ?")//每天凌晨两点执行
//	@Scheduled(cron = "0 0 0/1 * * *")  
//	@Scheduled(cron = "0 0/1 * * * *") //每分钟运行 
//	@Scheduled(cron = "0/1 * * * * *")  
	/**
	 * 每天计算
	 */
	public void pay() {
		log.info("定时器启动");
		List<Partner> list = partnerService.findAll();
		for(Partner  partner:list) {
			 balanceService.getTodayIncomeAndBack(partner);
			 balanceService.getBalance(partner);
		}
	
	}
}
