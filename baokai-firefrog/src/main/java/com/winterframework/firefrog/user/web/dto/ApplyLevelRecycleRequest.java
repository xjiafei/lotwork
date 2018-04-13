package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;
import java.util.Date;

public class ApplyLevelRecycleRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//用户名
	private String account;
	
	//用户ID
	private Long userId;
	
	//所属总代
	private String topAgent;
		
	//可用余额
	private Long availBal;
	
	//PT可用余额
	private Long availPtBal;
	
	//回收原因
	private String recycleReason;
	
	//操作人
	private String operator;
	
	//申请时间
	private Date createDate;
	
	//申请状态
	private Integer taskStatus;
	
	//最后登录时间
	private Date lastLoginDate;
	
	//最后登录IP
	private Long lastLoginIp;
	
	//最后登录位址
	private String lastLoginAddress;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTopAgent() {
		return topAgent;
	}
	public void setTopAgent(String topAgent) {
		this.topAgent = topAgent;
	}
	public Long getAvailBal() {
		return availBal;
	}
	public void setAvailBal(Long availBal) {
		this.availBal = availBal;
	}
	public String getRecycleReason() {
		return recycleReason;
	}
	public void setRecycleReason(String recycleReason) {
		this.recycleReason = recycleReason;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Long getAvailPtBal() {
		return availPtBal;
	}
	public void setAvailPtBal(Long availPtBal) {
		this.availPtBal = availPtBal;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public Long getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(Long lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public String getLastLoginAddress() {
		return lastLoginAddress;
	}
	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}		

}
