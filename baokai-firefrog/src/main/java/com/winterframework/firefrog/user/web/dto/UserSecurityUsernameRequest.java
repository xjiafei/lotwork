package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.winterframework.modules.validate.FFLength;

public class UserSecurityUsernameRequest implements Serializable {

	private static final long serialVersionUID = -3164015481022147500L;

	@NotNull
	@NotEmpty
	@FFLength(max = 30, min = 2)
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
