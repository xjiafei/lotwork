package com.winterframework.firefrog.sample.web;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class User {
	private String userName;
	private String passwd;
	@JsonSerialize(using = FirefrogDateSerializer.class)
	private Date startTime = new Date();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
