package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Ray.Wang
 *
 */
public class BeginFileUploadStatusRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5977613864046721911L;
	private List<String> accounts;
	private String status;
	private String curUser;
	
	public String getCurUser() {
		return curUser;
	}
	public void setCurUser(String curUser) {
		this.curUser = curUser;
	}
	public List<String> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}


