package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;
import java.util.Date;


public class GlobalGrayListRequest implements Serializable {


	private static final long serialVersionUID = 3919995572627700354L;

	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	
}
