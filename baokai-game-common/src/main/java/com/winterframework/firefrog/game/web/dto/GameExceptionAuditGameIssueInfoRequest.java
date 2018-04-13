package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: LotteryMonitorListRequest 
* @Description: 获取奖期异常审核奖期信息request
* @author Richard
* @date 2013-10-12 上午10:24:58 
*
 */
public class GameExceptionAuditGameIssueInfoRequest implements Serializable {

	private static final long serialVersionUID = 1351515869663983756L;
	private Long lotteryId;
    private Long issueCode;

	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}	
}
