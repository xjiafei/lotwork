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
public class ResponseHeader implements java.io.Serializable{
	/**
	 * 根据请求头反向生成相应头
	 */
	public static ResponseHeader createReponseHeader(RequestHeader req){
		ResponseHeader resp=new ResponseHeader();
		resp.sowner=req.getRowner();
		resp.rowner=req.getSowner();
		resp.sendtime=new Date(System.currentTimeMillis());
		resp.msn=req.getMsn();
		resp.msnsn=req.getMsnsn();
		resp.status=0L;
		resp.userId=req.getUserId();
		resp.setSessionId(req.getSessionId());
		return resp;		
	}
	/**
	 * 默認構造器置空
	 */
	private ResponseHeader(){		
	}
   /**
    * 發起方發送系統號
    */
	private long sowner=10;
	/**
	 * 接收方接受系統號
	 */
	private long rowner=11;
	
	private long msn;
	private long userId;
	
	private String sessionId;
	/**
	 * 通道msn
	 */
	private long msnsn;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date sendtime;
	/**
	 * 
	 */
	private long status;
	
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public long getSowner() {
		return sowner;
	}
	public long getRowner() {
		return rowner;
	}
	public long getMsn() {
		return msn;
	}
	public long getMsnsn() {
		return msnsn;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public long getUserId() {
		return userId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
		
}
