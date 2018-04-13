package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class GlobalGrayListStruc implements Serializable {

	private static final long serialVersionUID = -6628443060017336569L;
	
	private String account;
	private Long level;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long level) {
		this.level = level;
	}
	
	

	
}
