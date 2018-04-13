package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class SaveupDataRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4719754445251337828L;

	private String uid;//	欲充值uid

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
