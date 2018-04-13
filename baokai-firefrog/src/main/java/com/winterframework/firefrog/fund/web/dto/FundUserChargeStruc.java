/**   
* @Title: FundUserChargeStruc.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-12 下午2:39:11 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

/** 
* @ClassName: FundUserChargeStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-12 下午2:39:11 
*  
*/
public class FundUserChargeStruc implements Serializable{
	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;

	private String payBankName;
	
	private Long chargeAmt;

	private String rcvBankName;
	private String id;
	private String bankCardNo;

	//用户名
	private String rcvAccountName;

	//收款卡
	private String rcvMail;
	
	private String remark;
	
	//开户银行
	private String userBankName;
	private String bankUrl;
	
	private String breakUrl;
	
	private String nickName;
	private String bankAccount;
	private String bankNumber;

	public String getBankUrl() {
		return bankUrl;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public void setBankUrl(String bankUrl) {
		this.bankUrl = bankUrl;
	}

	public String getPayBankName() {
		return payBankName;
	}

	public void setPayBankName(String payBankName) {
		this.payBankName = payBankName;
	}

	public Long getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(Long chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public String getRcvBankName() {
		return rcvBankName;
	}

	public void setRcvBankName(String rcvBankName) {
		this.rcvBankName = rcvBankName;
	}

	public String getRcvAccountName() {
		return rcvAccountName;
	}

	public void setRcvAccountName(String rcvAccountName) {
		this.rcvAccountName = rcvAccountName;
	}

	public String getRcvMail() {
		return rcvMail;
	}

	public void setRcvMail(String rcvMail) {
		this.rcvMail = rcvMail;
	}

	public String getRemark() {
		return remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserBankName() {
		return userBankName;
	}

	public void setUserBankName(String userBankName) {
		this.userBankName = userBankName;
	}

	public String getBreakUrl() {
		return breakUrl;
	}

	public void setBreakUrl(String breakUrl) {
		this.breakUrl = breakUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
}
