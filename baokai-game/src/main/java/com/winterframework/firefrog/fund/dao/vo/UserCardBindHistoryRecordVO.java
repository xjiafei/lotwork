/**   
* @Title: UserCardBindHistoryRecordVO.java 
* @Package com.winterframework.firefrog.fund.dao.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-23 下午10:45:51 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName: UserCardBindHistoryRecordVO 
* @Description: 单用户绑卡历史记录VO
* @author Alan
* @date 2013-7-23 下午10:45:51 
*  
*/
public class UserCardBindHistoryRecordVO extends BaseEntity {

	private static final long serialVersionUID = 6976780699008878062L;

	//用户ID
	private Long userId;
	private String name;
	//动作
	private Long action;
	//操作时间
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
	private String account;
	private String topAcc;
	private Long isFreeze;
	private Long freezeMethod;
	private Long vipLvl;
	private Boolean isBlackList;
	

	public Long getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
