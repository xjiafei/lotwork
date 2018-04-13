package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: AuditLotteryIssueRequest 
* @Description: 5.5.43	彩种奖期监控-风险方案审核(OMI043) request
* @author Richard
* @date 2013-10-21 下午1:41:15 
*
 */
public class AuditLotteryIssueMonitorRequest implements Serializable {

	private static final long serialVersionUID = -4511395280609680347L;
	
	@NotNull
	private Long lotteryId;
	@NotNull
	private Long issueCode;
	
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	
	 
}
