/**   
* @Title: FundTransactionStrucForUI.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: winter-game-common.FundTransactionStrucForUI.java 
* @author Denny  
* @date 2014-6-5 下午3:13:41 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: FundTransactionStrucForUI 
* @Description: 交易记录页面显示 结构体
* @author Denny 
* @date 2014-6-5 下午3:13:41 
*  
*/
public class FundTransactionStrucForUI implements Serializable {

	private static final long serialVersionUID = -2045254314644168673L;

	/**交易流水号*/
	private String transactionId;
	/**交易时间*/
	private String transactionTime;
	/**摘要*/
	private String transactionInfp;
	/**收入金额*/
	private String transactionPositive;
	/**支付金额*/
	private String transactionNegative;
	/**账户余额*/
	private String accountBalance;
	
	/**总余额增加*/
	private String totalAccountAdd;
	
	/**总余额减少*/
	private String totalAccountReduce;
	
	/**可用余额增加*/
	private String availAccountAdd;
	/**可用余额减少*/
	private String availAccountReduce;
	/**冻结余额增加或减少*/
	private String freezeAccountChange;
	
	/**可用余额*/
	private String availBalance;
	

	private boolean freeze;

	private String note;

	private String account;

	/**冻结余额*/
	private String freezeAccount;

	private String planCode;
	private String orderCode;

	public boolean isFreeze() {
		if (transactionInfp != null && transactionInfp.contains("冻结")) {
			return true;
		}

		return freeze;
	}

	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getTransactionInfp() {
		return transactionInfp;
	}

	public void setTransactionInfp(String transactionInfp) {
		this.transactionInfp = transactionInfp;
	}

	public String getTransactionPositive() {
		return transactionPositive;
	}

	public void setTransactionPositive(String transactionPositive) {
		this.transactionPositive = transactionPositive;
	}

	public String getTransactionNegative() {
		return transactionNegative;
	}

	public void setTransactionNegative(String transactionNegative) {
		this.transactionNegative = transactionNegative;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
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

	public String getFreezeAccount() {
		return freezeAccount;
	}

	public void setFreezeAccount(String freezeAccount) {
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

	public String getTotalAccountAdd() {
		return totalAccountAdd;
	}

	public void setTotalAccountAdd(String totalAccountAdd) {
		this.totalAccountAdd = totalAccountAdd;
	}

	public String getTotalAccountReduce() {
		return totalAccountReduce;
	}

	public void setTotalAccountReduce(String totalAccountReduce) {
		this.totalAccountReduce = totalAccountReduce;
	}

	public String getAvailAccountAdd() {
		return availAccountAdd;
	}

	public void setAvailAccountAdd(String availAccountAdd) {
		this.availAccountAdd = availAccountAdd;
	}

	public String getAvailAccountReduce() {
		return availAccountReduce;
	}

	public void setAvailAccountReduce(String availAccountReduce) {
		this.availAccountReduce = availAccountReduce;
	}

	public String getFreezeAccountChange() {
		return freezeAccountChange;
	}

	public void setFreezeAccountChange(String freezeAccountChange) {
		this.freezeAccountChange = freezeAccountChange;
	}

	public String getAvailBalance() {
		return availBalance;
	}

	public void setAvailBalance(String availBalance) {
		this.availBalance = availBalance;
	}
	
}
