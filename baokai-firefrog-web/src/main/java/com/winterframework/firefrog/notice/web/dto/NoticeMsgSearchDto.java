package com.winterframework.firefrog.notice.web.dto;

import java.io.Serializable;
import java.util.Date;

public class NoticeMsgSearchDto implements Serializable {

	private static final long serialVersionUID = -3959244676221449236L;

	private String title;

	private String operater;

	private Date timeStart;

	private Date timeEnd;
	
	private Long sendSatus;
	
	private Long msgType;
	
	private Long pageNo;
	
	private Long status;
	
	private Date timeExpired;
	
	private Date gmtExpired;
	
	private String timeStartStr;
	
	private String timeEndStr;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public Long getSendSatus() {
		return sendSatus;
	}

	public void setSendSatus(Long sendSatus) {
		this.sendSatus = sendSatus;
	}

	public Long getMsgType() {
		return msgType;
	}

	public void setMsgType(Long msgType) {
		this.msgType = msgType;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getTimeExpired() {
		return timeExpired;
	}

	public void setTimeExpired(Date timeExpired) {
		this.timeExpired = timeExpired;
	}

	public Date getGmtExpired() {
		return gmtExpired;
	}

	public void setGmtExpired(Date gmtExpired) {
		this.gmtExpired = gmtExpired;
	}

	public String getTimeStartStr() {
		return timeStartStr;
	}

	public void setTimeStartStr(String timeStartStr) {
		this.timeStartStr = timeStartStr;
	}

	public String getTimeEndStr() {
		return timeEndStr;
	}

	public void setTimeEndStr(String timeEndStr) {
		this.timeEndStr = timeEndStr;
	}

}
