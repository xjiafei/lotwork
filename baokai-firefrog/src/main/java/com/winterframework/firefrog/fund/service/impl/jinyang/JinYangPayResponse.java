package com.winterframework.firefrog.fund.service.impl.jinyang;

import java.io.Serializable;

public class JinYangPayResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2915944590792082759L;
	private String rspCode;
	private String rspMsg;
	private JinYangPayDetailResponse data;
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
	public JinYangPayDetailResponse getData() {
		return data;
	}
	public void setData(JinYangPayDetailResponse data) {
		this.data = data;
	}

	
}
