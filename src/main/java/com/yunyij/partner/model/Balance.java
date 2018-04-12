package com.yunyij.partner.model;

import java.math.BigDecimal;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;
import com.github.jlf1997.spring_boot_sdk.oper.DBFinder;
import com.google.gson.annotations.Expose;

import io.swagger.annotations.ApiModelProperty;

/**
 * 合伙人收入
 * @author Administrator
 *
 */

@Entity
@Table(name = "td_partner_balance")
public class Balance extends BaseModel{
	
	  /**
     * 1：订单收入
     */
	@DBFinder(added=false)
   transient public static final int transactionType_IN = 1;
   
   /**
    * 2：提现支出
    */
   @DBFinder(added=false)
   transient public static final int transactionType_OUT = 2;
    
   /**
    * 4：退货支出
    */
   @DBFinder(added=false)
   transient public static final int transactionType_BACK = 4;
	
	/**
	 * 数据库主键
	 */
	@Id
	@ApiModelProperty(value="交易主键id")
	private Long balanceId;
	
	
	
	/**
	 * 金额
	 */
	@ApiModelProperty(value="交易金额")
	private String amount;
	
	/**
	 * 提成比例
	 */
	@ApiModelProperty(value="提成比例")
	private String rate;
	
	/**
	 * 账户所有余额
	 */
	@ApiModelProperty(value="账户所有余额")
	private String accountBalance;
	
	/**
	 * 金额类型1.订单收入2.退货支出4提现 
	 */
	@ApiModelProperty(value="交易类型",notes="1.订单收入2.退货支出4.提现")
	private Integer transactionType;

	
	/**
	 * 外部订单号
	 */
	@ApiModelProperty(value="外部id")
	private Long code;
	
	/**
	 * 订单号
	 */
	@ApiModelProperty(value="订单号")
	private Long orderNum;
	
	/**
	 * 商店
	 */
	@ManyToOne
	@JoinColumn(name="shop_id",
	foreignKey= @ForeignKey(value=ConstraintMode.NO_CONSTRAINT,foreignKeyDefinition="none"))
	@Expose(serialize=false)
	@DBFinder(added=false)
	private Shop shop;//商家信息
	

	
	@ApiModelProperty(hidden=true)
	private Integer state;


	

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}



	public Integer getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

	public Long getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(Long balanceId) {
		this.balanceId = balanceId;
	}



	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * 只需计算平台费率，配送费 优惠费已在订单模块计算完毕
	 * @param realAmountByRate 交易费率
	 * @param amount 当前用户所有余额
	 * @return 计算后的商店所有余额
	 */
	public BigDecimal getShopAllBalance(BigDecimal realAmountByRate, BigDecimal amount) {
		// TODO Auto-generated method stub
		BigDecimal balance = new BigDecimal(0);
		BigDecimal  transactionMoney =new BigDecimal(this.getAmount());
		//进位处理
		BigDecimal  realAmount = realAmountByRate.multiply(transactionMoney).setScale(2, BigDecimal.ROUND_HALF_UP);	
		this.setAmount(realAmount.toString());
		this.setRate(realAmountByRate.toString());
		switch(this.getTransactionType()) {
		case Balance.transactionType_IN:			
			balance = amount.add(realAmount);			
			break;
		case Balance.transactionType_OUT:			
			 balance = amount.subtract(realAmount);					
			 break;
		case Balance.transactionType_BACK:							
			balance = amount.subtract(realAmount);		
			 break;
		}			
		balance = balance.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.setAccountBalance(balance.toString());
		return balance;
		
		
	}
	
	
}
