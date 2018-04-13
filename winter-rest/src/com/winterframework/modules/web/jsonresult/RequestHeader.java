/**
* Copyright (C) Administrator, 2010
*/
package com.winterframework.modules.web.jsonresult;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
* 
* @author Administrator(xuhengbiao@gmail.com) in 2010-7-2
* @since 1.0
*/
public class RequestHeader {
   /**
    * 發起方發送系統號
    */
	private long sowner;
	/**
	 * 接收方接受系統號
	 */
	private long rowner;
	/**
	 * 通道
	 */
	private long msn;
	/**
	 * 通道msn
	 */
	private long msnsn;
	private long userId;
	private String userAccount;
	
	private String sessionId;
	/**
	 * 发起方发送时间
	 */
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date sendtime;
	private RequestClient client;
	public long getSowner() {
		return sowner;
	}
	public void setSowner(long sowner) {
		this.sowner = sowner;
	}
	public long getRowner() {
		return rowner;
	}
	public void setRowner(long rowner) {
		this.rowner = rowner;
	}
	public long getMsn() {
		return msn;
	}
	public void setMsn(long msn) {
		this.msn = msn;
	}
	public long getMsnsn() {
		return msnsn;
	}
	public void setMsnsn(long msnsn) {
		this.msnsn = msnsn;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public RequestClient getClient() {
		return client;
	}
	public void setClient(RequestClient client) {
		this.client = client;
	}
	
	
}
