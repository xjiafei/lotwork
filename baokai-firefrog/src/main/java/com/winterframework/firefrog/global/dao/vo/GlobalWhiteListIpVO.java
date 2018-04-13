package com.winterframework.firefrog.global.dao.vo;


import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GlobalWhiteListIpVO extends BaseEntity {
	
	private static final long serialVersionUID = 9164017369517998155L;
	//alias
	public static final String TABLE_ALIAS = "GlobalWhiteListIp";
	public static final String ALIAS_ID = "序號";
	public static final String ALIAS_IP_ADDR = "ip(可*)";
	public static final String ALIAS_USER_ACCUNT = "用戶名";
	public static final String ALIAS_STATUS = "狀態 0:關閉 1:開啟";
	public static final String ALIAS_REMARK = "備註";
	public static final String ALIAS_CREATE_USER = "建立人員";
	public static final String ALIAS_CREATE_TIME = "建立時間";
	public static final String ALIAS_UPDATE_USER = "更新人員";
	public static final String ALIAS_UPDATE_TIME = "更新時間";
	
	//date formats
	
	//columns START
	private Long id;
	private String ipAddr;
	private String ipAddr_bk;
	private String userAccunt;
	private Long status;
	private String remark;
	private String createUser;
	private Date cerateTime;
	private String updateUser;
	private Date updateTime;
	//columns END
	
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCerateTime() {
		return cerateTime;
	}
	public void setCerateTime(Date cerateTime) {
		this.cerateTime = cerateTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserAccunt() {
		return userAccunt;
	}
	public void setUserAccunt(String userAccunt) {
		this.userAccunt = userAccunt;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getIpAddr_bk() {
		return ipAddr_bk;
	}
	public void setIpAddr_bk(String ipAddr_bk) {
		this.ipAddr_bk = ipAddr_bk;
	}
	
}

