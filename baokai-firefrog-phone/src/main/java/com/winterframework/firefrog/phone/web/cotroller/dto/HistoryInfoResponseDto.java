package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class HistoryInfoResponseDto implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3398343968307702137L;
	private Long lotteryid;//	彩种id
	private String issue;//	奖期
	private String code;//	开奖号码
	private String time;//	开奖时间
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
