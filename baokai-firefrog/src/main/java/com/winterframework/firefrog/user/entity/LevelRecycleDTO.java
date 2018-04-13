package com.winterframework.firefrog.user.entity;

import java.io.Serializable;
import java.util.Date;

public class LevelRecycleDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//回收ID
	private Long id;
	
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
	
	//操作者
	private String operator;
	
	//申请时间
	private Date createDate;
	
	//生效时间
	private Date activityDate;
		
	//更新时间
	private Date updateDate;
		
	//申请状态 0:未执行 1:执行中 2:执行成功 3:执行失败
	private Integer taskStatus;
	
	private Integer taskStatus2;
	
	//回收状态
	private String recycleStatus;
	
	private int startNo;
	
	private int endNo;
	
	private String sortColums;
	
	//最后登录时间
	private Date lastLoginDate;
	
	//最后登录IP
	private Long lastLoginIp;
	
	//最后登录位址
	private String lastLoginAddress;
	
	//是否已換過密碼
	private Boolean changePwd;
	
	private Long vipLvl;
	
	
	/**
	 * @return the vipLvl
	 */
	public Long getVipLvl() {
		return vipLvl;
	}
	/**
	 * @param vipLvl the vipLvl to set
	 */
	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}
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
	public int getStartNo() {
		return startNo;
	}
	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}
	public int getEndNo() {
		return endNo;
	}
	public void setEndNo(int endNo) {
		this.endNo = endNo;
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
	public Integer getTaskStatus2() {
		return taskStatus2;
	}
	public void setTaskStatus2(Integer taskStatus2) {
		this.taskStatus2 = taskStatus2;
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
	public Date getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getSortColums() {
		return sortColums;
	}
	public void setSortColums(String sortColums) {
		this.sortColums = sortColums;
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
	public Boolean getChangePwd() {
		return changePwd;
	}
	public void setChangePwd(Boolean changePwd) {
		this.changePwd = changePwd;
	}		

}
