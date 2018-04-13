/**   
* @Title: UserCardBindHistoryRecord.java 
* @Package com.winterframework.firefrog.fund.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午10:15:46 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.entity;

import java.util.Date;

/** 
* @ClassName: UserCardBindHistoryRecord 
* @Description: 银行卡历史绑定记录
* @author Alan
* @date 2013-7-23 下午10:15:46 
*  
*/
public class UserCardBindHistory {

	//用户ID
	private Long userId;
	//动作
	private Long action;
	private String account;
	//操作时间
	private Date actionTime;
	//卡信息
	private BankCard bankCard;
	private String topAcc;
	private Long isFreeze;
	private Long freezeMethod;
	
	private String nickName;
	private Long bindcardType;
	

	public String getTopAcc() {
		return topAcc;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	public Long getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Long isFreeze) {
		this.isFreeze = isFreeze;
	}

	public Long getFreezeMethod() {
		return freezeMethod;
	}

	public void setFreezeMethod(Long freezeMethod) {
		this.freezeMethod = freezeMethod;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAction() {
		return action;
	}

	public void setAction(Long action) {
		this.action = action;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public BankCard getBankCard() {
		return bankCard;
	}

	public void setBankCard(BankCard bankCard) {
		this.bankCard = bankCard;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getBindcardType() {
		return bindcardType;
	}

	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
	}

}
