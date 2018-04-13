package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ModifyLoginpassRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7118623080860698332L;
	
	private String oldpass;///	旧登入密码
	private String newpass;//	md5(新登入密码)
	private String confirmNewpass;//	md5(新登入密码)
	public String getOldpass() {
		return oldpass;
	}
	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
	}
	public String getNewpass() {
		return newpass;
	}
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}
	public String getConfirmNewpass() {
		return confirmNewpass;
	}
	public void setConfirmNewpass(String confirmNewpass) {
		this.confirmNewpass = confirmNewpass;
	}


}
