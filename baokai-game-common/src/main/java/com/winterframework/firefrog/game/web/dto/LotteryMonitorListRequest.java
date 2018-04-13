package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: LotteryMonitorListRequest 
* @Description: 5.5.31	彩种奖期监控列表(OMI031) 请求
* @author Richard
* @date 2013-10-12 上午10:24:58 
*
 */
public class LotteryMonitorListRequest implements Serializable {

	private static final long serialVersionUID = 1351515869663983756L;

	private Integer dateType; //0 全部 1今天 3为3天，7为一周
	private Long lotteryId;
	private Long startIssueTime;
	private Long endIssueTime;
	private Integer	issueType;
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
	
	/**
	 * 
	* @Title: getIssueType 
	* @Description: 0-全部；1-仅查看尚未结束的奖期;2-仅查看存在异常的奖期;3仅查看尚未结束的奖期并且仅查看存在异常的奖期
	* @return
	 */
	public Integer getIssueType() {
		return issueType;
	}
	
	/**
	 * 
	* @Title: setIssueType 
	* @Description: 0-全部；1-仅查看尚未结束的奖期;2-仅查看存在异常的奖期;3仅查看尚未结束的奖期并且仅查看存在异常的奖期
	* @param issueType
	 */
	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
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
	

}
