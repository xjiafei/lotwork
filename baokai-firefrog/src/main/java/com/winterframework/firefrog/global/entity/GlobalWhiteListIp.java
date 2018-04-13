package com.winterframework.firefrog.global.entity;

import java.util.Date;

public class GlobalWhiteListIp {

	private Long id;
	private String ipAddr;
	private String userAccunt;
	private Long status;
	private String remark;
	private String createUser;
	private Date cerateTime;
	private String updateUser;
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getUserAccunt() {
		return userAccunt;
	}
	public void setUserAccunt(String userAccunt) {
		this.userAccunt = userAccunt;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCerateTime() {
		return cerateTime;
	}
	public void setCerateTime(Date cerateTime) {
		this.cerateTime = cerateTime;
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
