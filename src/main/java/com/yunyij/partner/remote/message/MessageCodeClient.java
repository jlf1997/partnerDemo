package com.yunyij.partner.remote.message;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.jlf1997.spring_boot_sdk.response.ResponseObject;
import com.yunyij.partner.remote.message.fallback.BaseClientImpl;
import com.yunyij.partner.remote.message.vo.MessageCode;

@FeignClient(value = "${client.message.name}",fallback=BaseClientImpl.class)
public interface MessageCodeClient {
	
	@RequestMapping(value = "message/userRegist", method = RequestMethod.POST, consumes = "application/json")
	public ResponseObject<MessageCode> sendMessage(MessageCode messageCode);
}
