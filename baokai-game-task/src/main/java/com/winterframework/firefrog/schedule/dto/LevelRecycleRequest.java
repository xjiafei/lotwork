package com.winterframework.firefrog.schedule.dto;

import java.io.Serializable;
import java.util.Date;

public class LevelRecycleRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//用户名
	private String account;
	
	//用户ID
	private Long userId;
	
	//操作者
	private String operator;
	
	
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

}
