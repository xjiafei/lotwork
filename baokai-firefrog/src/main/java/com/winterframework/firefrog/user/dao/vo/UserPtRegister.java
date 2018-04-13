package com.winterframework.firefrog.user.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class UserPtRegister extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Long userId;
	private Long parentId;
	private Long type;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

}
