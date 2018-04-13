/**   
* @Title: FundSuspiciousCardResponse.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-25 上午10:15:51 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.Date;

/** 
* @ClassName: FundSuspiciousCardResponse 
* @Description: 可疑银行卡记录Response
* @author Alan
* @date 2013-7-25 上午10:15:51 
*  
*/
public class FundSuspiciousCardResponse {

	private String cardNumber;

	private Long id;

	private Date gmtCreated;

	private String creatorAccount;

	private String memo;
	private String topAcc;
	private String account;
	private String bankAcc;

	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	

	

	public String getTopAcc() {
		return topAcc;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public String getCreatorAccount() {
		return creatorAccount;
	}

	public void setCreatorAccount(String creatorAccount) {
		this.creatorAccount = creatorAccount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
