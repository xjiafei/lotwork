package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 5.5.34	彩种奖期监控日志(OMI034) 请求
* @ClassName: LotteryIssueMonitorLogRequest 
* @Description:
* @author Richard
* @date 2013-10-15 上午9:17:34 
*
 */
public class LotteryIssueMonitorLogRequest implements Serializable {

	private static final long serialVersionUID = -8565518827887364822L;

	private Long lotteryid;
	private Long startCreateTime;
	private Long endCreateTime;
	private Integer warnType;
	private Integer dateType;
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Long getStartCreateTime() {
		return startCreateTime;
	}
	public void setStartCreateTime(Long startCreateTime) {
		this.startCreateTime = startCreateTime;
	}
	public Long getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(Long endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	public Integer getWarnType() {
		return warnType;
	}
	/**
	 * 
	* @Title: setWarnType 
	* @Description: 0 默认全部
		1 输入官方开奖号码
		2 输入官方开奖时间
		3 暂停派奖
		4继续派奖
		5撤销该期
		6撤销追号
	* @param warnType
	 */
	public void setWarnType(Integer warnType) {
		this.warnType = warnType;
	}
	public Integer getDateType() {
		return dateType;
	}
	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}
	
}
