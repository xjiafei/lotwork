package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

public class LevelRecycleRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//用户名
	private String account;
	
	//用户ID
	private Long userId;
	
	//操作者
	private String operator;
	
	//回收狀態
	private String recycleStatus;
	
	//回收ID
	private Long id;
	
	//是否更新密碼
	private Boolean changePwd;
	
	
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRecycleStatus() {
		return recycleStatus;
	}
	public void setRecycleStatus(String recycleStatus) {
		this.recycleStatus = recycleStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getChangePwd() {
		return changePwd;
	}
	public void setChangePwd(Boolean changePwd) {
		this.changePwd = changePwd;
	}	

}
