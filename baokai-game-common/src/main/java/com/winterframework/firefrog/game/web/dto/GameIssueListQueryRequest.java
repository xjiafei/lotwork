package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class GameIssueListQueryRequest implements Serializable {

	private static final long serialVersionUID = 1429388665080304524L;

	/** 彩种 */
	@NotNull
	private Long lotteryId;
	/** 状态:1 过去奖期 2 未来奖期 3 未来奖期（包括待审核） */
	@NotNull
	private Integer queryType;
	/** 开始时间 */
	private Long showStartTime;
	/** 结束时间 */
	private Long showEndTime;
	
	private Long checkAudit;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}


	public Long getShowStartTime() {
		return showStartTime;
	}

	public void setShowStartTime(Long showStartTime) {
		this.showStartTime = showStartTime;
	}

	public Long getShowEndTime() {
		return showEndTime;
	}

	public void setShowEndTime(Long showEndTime) {
		this.showEndTime = showEndTime;
	}

	public Long getCheckAudit() {
		return checkAudit;
	}

	public void setCheckAudit(Long checkAudit) {
		this.checkAudit = checkAudit;
	}
}
