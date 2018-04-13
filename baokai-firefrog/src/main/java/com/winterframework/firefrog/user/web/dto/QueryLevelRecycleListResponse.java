package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;
import java.util.Date;

public class QueryLevelRecycleListResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//用户Id
	private Long userId;
	
	//用户名
	private String account;
	
	//所属总代
	private String topAgent;
	

	//用户组
	private Integer userLvl;
	
	private Integer vipLvl;
	//可用余额
	private Long availBal;
	
	//PT可用余额
	private Long availPtBal;
	
	//最后登入时间
	private Date lastLoginDate;
	
	//最后登入IP
	private Long lastLoginIp;
	
	//最后登入位址
	private String lastLoginAddress;
	
	/**
	 * @return the vipLvl
	 */
	public Integer getVipLvl() {
		return vipLvl;
	}
	/**
	 * @param vipLvl the vipLvl to set
	 */
	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getUserLvl() {
		return userLvl;
	}
	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
	}
	public Long getAvailBal() {
		return availBal;
	}
	public void setAvailBal(Long availBal) {
		this.availBal = availBal;
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
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public Long getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(Long lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Long getAvailPtBal() {
		return availPtBal;
	}
	public void setAvailPtBal(Long availPtBal) {
		this.availPtBal = availPtBal;
	}
	public String getLastLoginAddress() {
		return lastLoginAddress;
	}
	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}	

}
