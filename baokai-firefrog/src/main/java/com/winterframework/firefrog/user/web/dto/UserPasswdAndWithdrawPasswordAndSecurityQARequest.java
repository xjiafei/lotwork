package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.winterframework.modules.validate.FFLength;

public class UserPasswdAndWithdrawPasswordAndSecurityQARequest implements Serializable {

	private static final long serialVersionUID = -3551275017733486564L;

	private QuStrucResponse quStruc[];

	@NotNull
	@NotEmpty
	@FFLength(max = 32, min = 32)
	private String withdrawPasswd;

	@NotNull
	@NotEmpty
	@FFLength(max = 32, min = 32)
	private String passwd;

	@Min(1)
	@Max(3)
	private int passwdLvl;

	public QuStrucResponse[] getQuStruc() {
		return quStruc;
	}

	public void setQuStruc(QuStrucResponse[] quStruc) {
		this.quStruc = quStruc;
	}

	public String getWithdrawPasswd() {
		return withdrawPasswd;
	}

	public void setWithdrawPasswd(String withdrawPasswd) {
		this.withdrawPasswd = withdrawPasswd;
	}

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
