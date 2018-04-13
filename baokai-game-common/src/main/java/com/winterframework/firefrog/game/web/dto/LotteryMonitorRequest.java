package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: LotteryMonitorRequest 
* @Description:5.5.33	彩种奖期监控-风险详情(OMI033)
* @author Richard
* @date 2013-10-12 上午10:39:30 
*
 */
public class LotteryMonitorRequest implements Serializable {

	private static final long serialVersionUID = -7748932508924849298L;

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
