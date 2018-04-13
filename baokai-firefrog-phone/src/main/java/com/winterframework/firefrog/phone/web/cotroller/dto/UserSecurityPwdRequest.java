package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserSecurityPwdRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3026567574635103112L;
	private String passwd;
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
	private int passwdLvl;

}
