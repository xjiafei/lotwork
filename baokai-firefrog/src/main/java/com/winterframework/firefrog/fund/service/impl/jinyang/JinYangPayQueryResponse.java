package com.winterframework.firefrog.fund.service.impl.jinyang;

import java.io.Serializable;

public class JinYangPayQueryResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3974042028491930487L;
	private String rspCode;
	private String rspMsg;
	private JinYangPayQueryDetailResponse data;
	public String getRspCode() {
		return rspCode;
	}
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}
	public String getRspMsg() {
		return rspMsg;
	}
	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}
	public JinYangPayQueryDetailResponse getData() {
		return data;
	}
	public void setData(JinYangPayQueryDetailResponse data) {
		this.data = data;
	}

	
}
