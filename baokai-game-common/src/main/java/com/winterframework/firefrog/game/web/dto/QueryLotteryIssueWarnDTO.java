package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

public class QueryLotteryIssueWarnDTO implements Serializable {

	private static final long serialVersionUID = -642686497505967643L;

	private Long lotteryid;
	private Date startCreateTime;
	private Date endCreateTime;
	private Integer warnType;
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Date getStartCreateTime() {
		return startCreateTime;
	}
	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}
	public Date getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	public Integer getWarnType() {
		return warnType;
	}
	public void setWarnType(Integer warnType) {
		this.warnType = warnType;
	}

}
