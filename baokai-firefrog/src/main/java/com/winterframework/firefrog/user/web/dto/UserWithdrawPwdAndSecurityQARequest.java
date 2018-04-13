package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.winterframework.modules.validate.FFLength;

public class UserWithdrawPwdAndSecurityQARequest implements Serializable {

	private static final long serialVersionUID = 6965129187600059695L;

	private QuStrucResponse quStruc[];

	@NotNull
	@NotEmpty
	@FFLength(max = 32, min = 32)
	private String withdrawPasswd;
	//1 安全问题  2安全密码  3：both
	private Integer type;

	public String getWithdrawPasswd() {
		return withdrawPasswd;
	}

	public void setWithdrawPasswd(String withdrawPasswd) {
		this.withdrawPasswd = withdrawPasswd;
	}

	public QuStrucResponse[] getQuStruc() {
		return quStruc;
	}

	public void setQuStruc(QuStrucResponse[] quStruc) {
		this.quStruc = quStruc;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	

}
