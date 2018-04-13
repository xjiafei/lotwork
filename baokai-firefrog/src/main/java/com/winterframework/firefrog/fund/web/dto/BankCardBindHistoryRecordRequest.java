/**   
* @Title: BankCardBindHistoryRecordRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午10:00:51 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: BankCardBindHistoryRecordRequest 
* @Description: 绑卡历史记录Request
* @author Alan
* @date 2013-7-23 下午10:00:51 
*  
*/
public class BankCardBindHistoryRecordRequest {

	//用户名
	private Long userId;
	private String userAccount;
	private String bankCard;
	private Long bindcardType;
	

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Long getBindcardType() {
		return bindcardType;
	}

	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
	}

}
