package com.yunyij.partner;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.jlf1997.spring_boot_sdk.convert.EnableHttpConvert;
import com.github.jlf1997.spring_boot_sdk.swagger.EnableSwagger2Doc;

@SpringCloudApplication
@EnableFeignClients
@EnableSwagger2Doc
@EnableHttpConvert
@EnableScheduling
public class PartnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartnerApplication.class, args);
	}
	
}
