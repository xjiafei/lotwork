package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;

/**
 * 
 * @author Ami.Tsai
 *
 */
public class BackendCancelMissionRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4333557935799732287L;
	private String account;
	private String reason;
	private String canceluser;
	
	public String getCanceluser() {
		return canceluser;
	}
	public void setCanceluser(String canceluser) {
		this.canceluser = canceluser;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
}
