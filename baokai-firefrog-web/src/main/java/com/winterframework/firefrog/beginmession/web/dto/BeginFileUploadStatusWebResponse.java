package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Ray.Wang
 *
 */
public class BeginFileUploadStatusWebResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5977613864046721911L;
	private Long status;
	private String message;
	private List<String> nonAccount;
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getNonAccount() {
		return nonAccount;
	}
	public void setNonAccount(List<String> nonAccount) {
		this.nonAccount = nonAccount;
	}
		
}


