/**   
* @Title: BankCardQueryBindRecordResponse.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-19 下午1:44:31 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.List;

/** 
* @ClassName: BankCardQueryBindRecordResponse 
* @Description: 绑卡记录 response
* @author Alan
* @date 2013-7-19 下午1:44:31 
*  
*/
public class BankCardQueryBindRecordResponse {

	//锁定记录id
	private Long lockedId;
	//用户名
	private String account;
	private String topAcc;
	//用户组
	private Long userLvl;
	private Long vipLvl;
	//绑定数量
	private Long bindCount;
	//状态
	private Long status;
	//操作人
	private String operator;
	//绑定卡号组
	private List<UserBankStruc> userBanks;

	public Long getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

	public String getTopAcc() {
		return topAcc;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	public Long getLockedId() {
		return lockedId;
	}

	public void setLockedId(Long lockedId) {
		this.lockedId = lockedId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Long userLvl) {
		this.userLvl = userLvl;
	}

	public Long getBindCount() {
		return bindCount;
	}

	public void setBindCount(Long bindCount) {
		this.bindCount = bindCount;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<UserBankStruc> getUserBanks() {
		return userBanks;
	}

	public void setUserBanks(List<UserBankStruc> userBanks) {
		this.userBanks = userBanks;
	}

}
