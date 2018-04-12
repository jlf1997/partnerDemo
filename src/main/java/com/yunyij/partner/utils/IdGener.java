package com.yunyij.partner.utils;



public class IdGener {
	private SnowflakeIdWorker idForRejectOrder;
	private SnowflakeIdWorker idForCode;
	private SnowflakeIdWorker id;
	private static class SingletonHolder {    
	       /**  
	         * 单例对象实例  
	         */    
	        static final IdGener INSTANCE = new IdGener();    
	    }    
	     
	    public static IdGener getInstance() {    
	       return SingletonHolder.INSTANCE;    
	    }    
	   /**  
	     * private的构造函数用于避免外界直接使用new来实例化对象  
	     */    
	    private IdGener() {  
	    	idForCode = new SnowflakeIdWorker(0, 0);
	    	idForRejectOrder = new SnowflakeIdWorker(1, 0);
	    	id = new SnowflakeIdWorker(2, 0);
	    }    
	     
	    /**  
	    * readResolve方法应对单例对象被序列化时候  
	     */    
	    private Object readResolve() {    
	        return getInstance();    
	   }    
	    /**
	     * 获取订单编号
	     * @return
	     */
	    public  long getCodeId() {
	    	return idForCode.nextId();
	    }
	    
	    public  long getId() {
	    	return idForCode.nextId();
	    }
	    /**
	     * 获取退货单号
	     * @return
	     */
	    public  long getRejectOrderId() {
	    	return idForRejectOrder.nextId();
	    }
}
