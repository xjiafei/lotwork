package com.winterframework.firefrog.beginmession.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Ray.Wang
 *
 */
public class CancelListData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5821570104363677540L;

	private String accountName; 
	
	private Date cancelTime;
	
	private String cancelReason;
	
	private String cancelUser;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}	

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(String cancelUser) {
		this.cancelUser = cancelUser;
	}

	
}