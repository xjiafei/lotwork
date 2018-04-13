package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

public class LotteryMonitorListRequestDTO implements Serializable {

	private static final long serialVersionUID = -451488490532586722L;

	private Date startIssueTime;
	private Date endIssueTime;
	private Integer issueType;
	private Long lotteryId;
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
	public Integer getIssueType() {
		return issueType;
	}
	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	
}
