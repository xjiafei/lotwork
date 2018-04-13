package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: LotteryMonitorDetailRequest 
* @Description: 5.5.32	彩种奖期监控详情(OMI032)
* @author Richard
* @date 2013-10-12 上午10:32:23 
*
 */
public class LotteryMonitorDetailRequest implements Serializable {

	private static final long serialVersionUID = 9083862505712032508L;
	
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
