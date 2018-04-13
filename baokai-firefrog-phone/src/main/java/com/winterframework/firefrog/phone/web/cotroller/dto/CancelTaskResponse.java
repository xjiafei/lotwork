package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class CancelTaskResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -447894232896185700L;
	private String status;//	返回状态
//	private Integer messagetype;//	返回状态代码
//	private String content;//	错误说明
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*public Integer getMessagetype() {
		return messagetype;
	}
	public void setMessagetype(Integer messagetype) {
		this.messagetype = messagetype;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}*/

}
