package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

public class UserDetailResponse implements Serializable {

	private static final long serialVersionUID = 6986457746481253676L;

	private UserStrucResponse userStruc;

	private LoginStrucResponse[] loginStruc;

	public UserStrucResponse getUserStruc() {
		return userStruc;
	}

	public void setUserStruc(UserStrucResponse userStruc) {
		this.userStruc = userStruc;
	}

	public LoginStrucResponse[] getLoginStruc() {
		return loginStruc;
	}

	public void setLoginStruc(LoginStrucResponse[] loginStruc) {
		this.loginStruc = loginStruc;
	}

}
