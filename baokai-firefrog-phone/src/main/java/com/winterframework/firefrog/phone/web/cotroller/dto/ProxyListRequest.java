package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ProxyListRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1916496140932557589L;
	
	private Integer type;//	用户类型
	private Integer p;//	第几页
	private String uid;//	请求查询的用户id
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getP() {
		return p;
	}
	public void setP(Integer p) {
		this.p = p;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

}
