package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

public class GameExceptionAuditRequestDTO implements Serializable {

	private static final long serialVersionUID = -451488490532586722L;

	private Date startIssueTime;
	private Date endIssueTime;
	private Long lotteryId;
	private Integer status;
	public Date getStartIssueTime() {
		return startIssueTime;
	}
	public void setStartIssueTime(Date startIssueTime) {
		this.startIssueTime = startIssueTime;
	}
	public Date getEndIssueTime() {
		return endIssueTime;
	}
	public void setEndIssueTime(Date endIssueTime) {
		this.endIssueTime = endIssueTime;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
