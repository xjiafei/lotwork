package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class PtRegisterRequest implements Serializable{
		
	private String username;
	private String password1;
	private String password2;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
}
