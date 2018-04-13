package com.winterframework.firefrog.acl.web.dto;

import java.io.Serializable;

public class AclUserPwdRequest implements Serializable {

	private static final long serialVersionUID = 9055026545168412625L;

	private Long id;
	
	private String oldPwd;

	private String newPwd;

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
