package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserSecurityEmailRequest implements Serializable {

	private static final long serialVersionUID = 4245741188258823017L;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
