/**   
* @Title: BankCardHistoryStruc.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-24 上午10:12:19 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

/** 
* @ClassName: BankCardHistoryStruc 
* @Description: 绑卡历史记录
* @author Alan
* @date 2013-7-24 上午10:12:19 
*  
*/
public class BankCardHistoryStruc implements Serializable {

	private static final long serialVersionUID = 5051125425389301127L;

	//用户ID
	private Long userId;
	//动作
	private Long action;
	//操作时间
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	private Date actionTime;
	//银行ID
	private Long bankId;
	//银行账户
	private String bankAccount;
	//省
	private String province;
	//市
	private String city;
	//银行支行
	private String branchName;
	private Long mcBankId;
	private String bankNumber;
	private String topAcc;
	private Long isFreeze;
	private Long freezeMethod;
	private String account;
	private Boolean isBlackList;
	private String nickName;
	private Long bindcardType;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUserId() {
		return userId;
	}

	public String getTopAcc() {
		return topAcc;
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

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getMcBankId() {
		return mcBankId;
	}

	public void setMcBankId(Long mcBankId) {
		this.mcBankId = mcBankId;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public Boolean getIsBlackList() {
		return isBlackList;
	}

	public void setIsBlackList(Boolean isBlackList) {
		this.isBlackList = isBlackList;
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