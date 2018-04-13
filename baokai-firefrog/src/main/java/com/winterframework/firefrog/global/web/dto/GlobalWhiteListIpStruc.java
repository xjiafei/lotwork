package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;
import java.util.Date;



public class GlobalWhiteListIpStruc implements Serializable {

	private static final long serialVersionUID = -6628443060017336569L;
	
	private Long id;
	private String ipAddr;
	private String userAcunt;
	private String country;	
	private String operator;
	private String operationTime;
	private String remark;
	
	
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getUserAcunt() {
		return userAcunt;
	}
	public void setUserAcunt(String userAcunt) {
		this.userAcunt = userAcunt;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
