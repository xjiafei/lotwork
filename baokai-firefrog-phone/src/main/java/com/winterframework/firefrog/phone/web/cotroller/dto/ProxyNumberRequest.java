package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ProxyNumberRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8583734841487226492L;
	
	private String uid;//请求查询的用户id

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
