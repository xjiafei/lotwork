package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CancelGameResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2842144270067927716L;

	private String status;//	返回状态	varchar	"success"	
//	private Integer messagetype	;//返回状态代码	int	0为成功，大于0表失败	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
//	public Integer getMessagetype() {
//		return messagetype;
//	}
//	public void setMessagetype(Integer messagetype) {
//		this.messagetype = messagetype;
//	}

	
}
