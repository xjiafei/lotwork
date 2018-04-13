package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserDetailResponse implements Serializable{

	private static final long serialVersionUID = 5673260102265563143L;
	
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
