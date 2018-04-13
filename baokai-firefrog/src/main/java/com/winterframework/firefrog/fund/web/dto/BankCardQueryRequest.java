/**   
* @Title: BankCardQueryRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: 查询绑定银行卡请求参数DTO  
* @author Denny  
* @date 2013-7-2 上午9:26:17 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: BankCardQueryRequest 
* @Description: 查询绑定银行卡请求参数DTO 
* @author Denny 
* @date 2013-7-2 上午9:26:17 
*  
*/
public class BankCardQueryRequest {

	private long userId;
	private String bankCard;
	private long bindCardType;
	private String bankNumber;
	private String bankAccount;
	private String nickName;
	private String nickNameMust;
	

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getBindCardType() {
		return bindCardType;
	}

	public void setBindCardType(long bindCardType) {
		this.bindCardType = bindCardType;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickNameMust() {
		return nickNameMust;
	}

	public void setNickNameMust(String nickNameMust) {
		this.nickNameMust = nickNameMust;
	}
}
