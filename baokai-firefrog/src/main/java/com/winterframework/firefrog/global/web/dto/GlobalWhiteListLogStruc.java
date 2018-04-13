package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;
import java.util.Date;


public class GlobalWhiteListLogStruc implements Serializable {

	private static final long serialVersionUID = 2805982498526383676L;
	
	private Long id;
	private String whiteListIP;
	private String accunt;
	private String country;	
	private String operator;
	private String operationTime;
	private String status;
	private Long listIP;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWhiteListIP() {
		return whiteListIP;
	}
	public void setWhiteListIP(String whiteListIP) {
		this.whiteListIP = whiteListIP;
	}
	public String getAccunt() {
		return accunt;
	}
	public void setAccunt(String accunt) {
		this.accunt = accunt;
	}
	public Long getListIP() {
		return listIP;
	}
	public void setListIP(Long listIP) {
		this.listIP = listIP;
	}
	
}
