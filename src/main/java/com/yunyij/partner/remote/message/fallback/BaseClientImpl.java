package com.yunyij.partner.remote.message.fallback;

import org.springframework.stereotype.Component;

import com.github.jlf1997.spring_boot_sdk.response.ResponseObject;
import com.yunyij.partner.remote.message.MessageCodeClient;
import com.yunyij.partner.remote.message.vo.MessageCode;

@Component
public class BaseClientImpl implements MessageCodeClient {

	@Override
	public ResponseObject<MessageCode> sendMessage(MessageCode messageCode) {
	
		return new ResponseObject<MessageCode>().failure("短信消息服务不可用");
	}

}
