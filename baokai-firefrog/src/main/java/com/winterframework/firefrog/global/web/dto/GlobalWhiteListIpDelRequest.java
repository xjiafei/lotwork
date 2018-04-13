package com.winterframework.firefrog.global.web.dto;

import java.sql.Date;
import java.util.List;

public class GlobalWhiteListIpDelRequest {
	
private List<Long> ids;
	
	private String updateUser;
	private Date updateTime;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
