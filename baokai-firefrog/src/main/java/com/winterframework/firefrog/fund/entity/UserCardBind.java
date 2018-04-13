/**   
* @Title: BankCardBindOrder.java 
* @Package com.winterframework.firefrog.fund.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-22 下午2:24:14 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.entity;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.user.entity.User;

/** 
* @ClassName: BankCardBindOrder 
* @Description: 银行卡绑定记录
* @author Alan
* @date 2013-7-22 下午2:24:14 
*  
*/
public class UserCardBind {

	//对应用户
	private User bindUser;
	//绑卡记录
	private List<BankCard> bankCards;
	//锁定记录id
	private Long lockId;
	//状态(1启用中、2锁定中)
	private Long status;
	//操作人
	private String operator;
	//创建时间
	private Date gmtCreated;
	
	public User getBindUser() {
		return bindUser;
	}
	public void setBindUser(User bindUser) {
		this.bindUser = bindUser;
	}
	public List<BankCard> getBankCards() {
		return bankCards;
	}
	public void setBankCards(List<BankCard> bankCards) {
		this.bankCards = bankCards;
	}
	public Long getLockId() {
		return lockId;
	}
	public void setLockId(Long lockId) {
		this.lockId = lockId;
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
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

}
