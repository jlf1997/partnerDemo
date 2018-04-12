package com.yunyij.partner.remote.order.vo;

public class OrderBackStateType {

	public static final int REDAY = 1;
	
	public static final int AGGREE = 2;
	
	public static final int REFUSE = 4;
	
	public static final int REFUSE_GET = 8;
	
	public static final int AGGREE_GET = 16;
	
	public static String getDes(int type) {
		String des = "";
		switch(type) {
		case REDAY : 
			des = "退单申请中";
			break;
		case AGGREE : 
			des = "商家同意退单";
			break;
		case REFUSE : 
			des = "商家拒绝退单";
		case REFUSE_GET : 
			des = "商家拒绝收货";
		case AGGREE_GET : 
			des = "商家确认收货";
			break;		
		}
		return des;
	}
}
