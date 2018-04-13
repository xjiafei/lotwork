package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: FundTransactionStruc 
* @Description: 资金交易明细
* @author Alan
* @date 2013-10-14 下午4:11:59 
*  
*/
public class FundTransactionStruc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -348293346294944147L;
	/**交易流水号*/
	private String transactionId;
	/**交易时间*/
	private Date transactionTime;
	/**摘要*/
	private String transactionInfp;
	/**收入金额*/
	private Long transactionPositive;
	/**支付金额*/
	private Long transactionNegative;
	
	/**总余额增加*/
	private Long totalAccountAdd;
	
	/**总余额减少*/
	private Long totalAccountReduce;
	
	/**可用余额增加*/
	private Long availAccountAdd;
	/**可用余额减少*/
	private Long availAccountReduce;
	/**冻结余额增加或减少*/
	private Long freezeAccountChange;
	
	/**可用余额*/
	private Long availBalance;
	
	/**冻结余额*/
	private Long freezeAccount;
	/**账户总余额*/
	private Long accountBalance;
	
	private boolean freeze;
	
	private String note;
	
	private String account;
	
	private String planCode;
	private String orderCode;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getTransactionInfp() {
		return transactionInfp;
	}
	public void setTransactionInfp(String transactionInfp) {
		this.transactionInfp = transactionInfp;
	}
	public Long getTransactionPositive() {
		return transactionPositive;
	}
	public void setTransactionPositive(Long transactionPositive) {
		this.transactionPositive = transactionPositive;
	}
	public Long getTransactionNegative() {
		return transactionNegative;
	}
	public void setTransactionNegative(Long transactionNegative) {
		this.transactionNegative = transactionNegative;
	}
	public Long getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Long accountBalance) {
		this.accountBalance = accountBalance;
	}
	public boolean isFreeze() {
		return freeze;
	}
	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getFreezeAccount() {
		return freezeAccount;
	}
	public void setFreezeAccount(Long freezeAccount) {
		this.freezeAccount = freezeAccount;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Long getTotalAccountAdd() {
		return totalAccountAdd;
	}
	public void setTotalAccountAdd(Long totalAccountAdd) {
		this.totalAccountAdd = totalAccountAdd;
	}
	public Long getTotalAccountReduce() {
		return totalAccountReduce;
	}
	public void setTotalAccountReduce(Long totalAccountReduce) {
		this.totalAccountReduce = totalAccountReduce;
	}
	public Long getAvailAccountAdd() {
		return availAccountAdd;
	}
	public void setAvailAccountAdd(Long availAccountAdd) {
		this.availAccountAdd = availAccountAdd;
	}
	public Long getAvailAccountReduce() {
		return availAccountReduce;
	}
	public void setAvailAccountReduce(Long availAccountReduce) {
		this.availAccountReduce = availAccountReduce;
	}
	public Long getFreezeAccountChange() {
		return freezeAccountChange;
	}
	public void setFreezeAccountChange(Long freezeAccountChange) {
		this.freezeAccountChange = freezeAccountChange;
	}
	public Long getAvailBalance() {
		return availBalance;
	}
	public void setAvailBalance(Long availBalance) {
		this.availBalance = availBalance;
	}
}
