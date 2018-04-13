package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class AddSecpassRequest implements Serializable {

	private static final long serialVersionUID = 5182886917886577791L;

	private String newpass2;//	md5(资金密码)
	private String confirmNewpass2;//	md5(资金密码)
	public String getNewpass2() {
		return newpass2;
	}
	public void setNewpass2(String newpass2) {
		this.newpass2 = newpass2;
	}
	public String getConfirmNewpass2() {
		return confirmNewpass2;
	}
	public void setConfirmNewpass2(String confirmNewpass2) {
		this.confirmNewpass2 = confirmNewpass2;
	}

}
