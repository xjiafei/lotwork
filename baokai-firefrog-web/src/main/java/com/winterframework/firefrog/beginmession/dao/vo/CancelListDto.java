package com.winterframework.firefrog.beginmession.dao.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Ray.Wang
 *
 */
public class CancelListDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5821570104363677540L;

	private String accountName; 
	
	private String cancelTime;
	
	private String cancelReason;
	
	private String cancelUser;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
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