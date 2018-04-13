/**   
* @Title: BankCardQueryBindRecordRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-19 上午11:13:22 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: BankCardQueryBindRecordRequest 
* @Description: 查询用户绑卡记录条件
* @author Alan
* @date 2013-7-19 上午11:13:22 
*  
*/
public class BankCardQueryBindRecordRequest {

	//用户名
	private String userAccount;
	//用户组
	private Long userLvl;
	//操作人
	private String operator;
	//银行卡
	private String bankCard;
	
	private Long bindcardType;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Long getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Long userLvl) {
		this.userLvl = userLvl;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public Long getBindcardType() {
		return bindcardType;
	}

	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
	}

	

}
