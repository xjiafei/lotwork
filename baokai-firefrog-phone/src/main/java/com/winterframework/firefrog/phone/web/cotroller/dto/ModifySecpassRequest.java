package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ModifySecpassRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4685257806069672570L;
	
	private String oldpass2;//	旧资金密码
	private String newpass2;//	md5(新资金密码)
	private String confirmNewpass2;//	md5(新资金密码)
	public String getOldpass2() {
		return oldpass2;
	}
	public void setOldpass2(String oldpass2) {
		this.oldpass2 = oldpass2;
	}
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
