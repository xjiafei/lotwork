package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;


/** 
* @ClassName ActivityUserAwardRequest 
* @Description 用户获奖记录
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivitySignUpRequest implements Serializable {

	private static final long serialVersionUID = 51874940978504543L;

	private Long actId;
	private String account;
	private Long   month;
	private Long week;
	private String source;
	private String startTime;
	private String endTime;
	private String token;
	public Long getActId() {
		return actId;
	}
	public void setActId(Long actId) {
		this.actId = actId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getMonth() {
		return month;
	}
	public void setMonth(Long month) {
		this.month = month;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getWeek() {
		return week;
	}
	public void setWeek(Long week) {
		this.week = week;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
