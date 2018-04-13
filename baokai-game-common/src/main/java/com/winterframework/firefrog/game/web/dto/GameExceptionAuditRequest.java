package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: LotteryMonitorListRequest 
* @Description: 游戏异常审核请求
* @author Richard
* @date 2013-10-12 上午10:24:58 
*
 */
public class GameExceptionAuditRequest implements Serializable {

	private static final long serialVersionUID = 1351515869663983756L;

	private Integer dateType; //0 全部 1今天 3为3天，7为一周
	private Long lotteryId;
	private Long startIssueTime;
	private Long endIssueTime;
	private Integer	status;//0全部，1 待处理 2 已完成
	public Long getStartIssueTime() {
		return startIssueTime;
	}
	public void setStartIssueTime(Long startIssueTime) {
		this.startIssueTime = startIssueTime;
	}
	public Long getEndIssueTime() {
		return endIssueTime;
	}
	public void setEndIssueTime(Long endIssueTime) {
		this.endIssueTime = endIssueTime;
	}
	
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Integer getDateType() {
		return dateType;
	}
	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	

}
