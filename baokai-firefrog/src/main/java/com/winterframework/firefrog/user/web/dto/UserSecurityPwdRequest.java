package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.winterframework.modules.validate.FFLength;

public class UserSecurityPwdRequest implements Serializable {

	private static final long serialVersionUID = -4965574725631582551L;

	@NotNull
	@NotEmpty
	@FFLength(max = 32, min = 32)
	private String passwd;

	@Min(1)
	@Max(3)
	private int passwdLvl;

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getPasswdLvl() {
		return passwdLvl;
	}

	public void setPasswdLvl(int passwdLvl) {
		this.passwdLvl = passwdLvl;
	}

}
