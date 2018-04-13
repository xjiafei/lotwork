package com.winterframework.firefrog.user.entity;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class UserPtRegisterRequestVo extends BaseEntity {

	private static final long serialVersionUID = 4391360208964183516L;

	private Long userId;
	private Long type;
	private Long status;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}

}
