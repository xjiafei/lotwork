package com.winterframework.firefrog.user.entity;

import java.io.Serializable;
import java.util.Date;

public class LevelRecycleDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//用户名
	private String account;
	
	//用户ID
	private Long userId;
	
	//所属总代
	private String topAgent;
		
	//可用余额
	private Long availBal;
	
	//回收原因
	private String recycleReason;
	
	//操作者
	private String operator;
	
	//申请时间
	private Date createDate;
		
	//申请状态 0:未执行 1:执行中 2:执行成功 3:执行失败
	private Integer taskStatus;	
	
	private Integer startNo;
	
	private Integer endNo;
	
	
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
	public Integer getStartNo() {
		return startNo;
	}
	public void setStartNo(Integer startNo) {
		this.startNo = startNo;
	}
	public Integer getEndNo() {
		return endNo;
	}
	public void setEndNo(Integer endNo) {
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

}
