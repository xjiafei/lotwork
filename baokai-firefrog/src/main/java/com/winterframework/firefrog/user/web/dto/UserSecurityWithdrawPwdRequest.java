package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.winterframework.modules.validate.FFLength;

public class UserSecurityWithdrawPwdRequest implements Serializable {

	private static final long serialVersionUID = 8546651781141916719L;

	@NotNull
	@NotEmpty
	@FFLength(max = 32, min = 32)
	private String withdrawPasswd;

	public String getWithdrawPasswd() {
		return withdrawPasswd;
	}

	public void setWithdrawPasswd(String withdrawPasswd) {
		this.withdrawPasswd = withdrawPasswd;
	}

}
