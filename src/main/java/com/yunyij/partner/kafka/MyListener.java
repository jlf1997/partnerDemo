package com.yunyij.partner.kafka;

import java.math.BigDecimal;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.github.jlf1997.tools.gson.GsonUtil;
import com.yunyij.partner.kafka.entity.TranslationLog;
import com.yunyij.partner.model.Balance;
import com.yunyij.partner.model.Shop;
import com.yunyij.partner.remote.order.vo.BackGoods;
import com.yunyij.partner.remote.order.vo.OrderView;
import com.yunyij.partner.remote.order.vo.PayLog;
import com.yunyij.partner.service.BalanceService;
import com.yunyij.partner.utils.IdGener;






@Component
public class MyListener {

	private static final Logger logger = LoggerFactory.getLogger(MyListener.class);
	

	@Autowired
	private KafkaTemplate kafkaTemplate;
	
	@Autowired
	private BalanceService balanceService;
	

	    
    @KafkaListener(topics = {"${topic.translation}"})
    public void listen1(ConsumerRecord<?, ?> record) throws InterruptedException {
    	if(record.key().equals("group-partner")) {
    		TranslationLog orderState = GsonUtil.jsonToObj(record.value().toString(), TranslationLog.class);
        	Balance tra = new Balance();
        	tra.setBalanceId(IdGener.getInstance().getId());
        	Shop sup = new Shop();
        	switch(orderState.getState()) {
        	case TranslationLog.ORDER_FINISH:
        		OrderView orderView = orderState.getOrderView();
        		tra.setAmount(orderView.getTotalPrice());  
        		tra.setCode(orderView.getOrderNum());
        	
        		tra.setOrderNum(orderView.getOrderNum());
        		tra.setTransactionType(Balance.transactionType_IN);	    		
        		sup.setShopCode(orderView.getShopid());
        		tra.setShop(sup);
        		break;
        	case TranslationLog.ORDER_BACK:
        		BackGoods backGoods = orderState.getBackGoods();
        		tra.setAmount(backGoods.getTotalPrice());
        		tra.setCode(backGoods.getBackNum());
        		tra.setOrderNum(backGoods.getOrderNum());
        		tra.setTransactionType(Balance.transactionType_BACK);	    		
        		sup.setShopCode(backGoods.getShopid());
        		tra.setShop(sup);
        		break;    			    		
        	}
        	
        	//提交更新
        	BigDecimal balance = updAB(tra,0);
        	if(balance!=null) {
        		//退货
        		if(Balance.transactionType_BACK==tra.getTransactionType()) {
        			//通知退货
        			BackGoods backGoods = orderState.getBackGoods();
        			PayLog payLog = new PayLog();
        			payLog.setAmount(backGoods.getTotalPrice());
        			payLog.setCode(backGoods.getBackNum());
        			payLog.setCodeType(PayLog.BACK_GOODS);
        			payLog.setOrderNum(backGoods.getOrderNum());
        			payLog.setShopid(backGoods.getShopid());
        			payLog.setUserid(backGoods.getUserid());
        			if(balance.intValue()>0) {
        				payLog.setPayState(PayLog.READEY);
        			}else {
        				payLog.setPayState(PayLog.NOT);
        			}
        			
        			logger.info("通知订单服务退货结果");
        		}
        		if(Balance.transactionType_IN==tra.getTransactionType()) {
        			
        			logger.info("通知订单服务退货结果");
        			
        		}
        	}
    	}else {
    		logger.info("no group:"+record.key());
    	}
    	
        
    }
    
    public BigDecimal updAB(Balance tra,Integer times) throws InterruptedException {
    	
    	try {
    		times++;
    		BigDecimal balance = balanceService.updateShopAccountBalance(tra);
    		logger.info("处理商户余额成功");	    		
    		return balance;
    	}catch (Exception e) {
    		if(times<10) {
    			return updAB(tra,times);
    		}else {
    			e.printStackTrace();
    			logger.error("处理商户余额失败：shopid:"+tra.getShop().getShopCode()+"orderNum:"+tra.getOrderNum()+e.getMessage());	    			
    			return null;
    		}
    		
		}
    		
    }
    
	    
	   
	    
	    
	    
	    
}
