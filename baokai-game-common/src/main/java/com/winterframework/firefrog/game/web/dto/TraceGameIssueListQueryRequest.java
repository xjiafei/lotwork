package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class TraceGameIssueListQueryRequest implements Serializable {

	private static final long serialVersionUID = 1429388665080304524L;

	/** 彩种 */
	@NotNull
	private Long lotteryId;

	/** 最大可追号期数*/
	@NotNull
	private Integer maxCountIssue;

	public Integer getMaxCountIssue() {
		return maxCountIssue;
	}

	public void setMaxCountIssue(Integer maxCountIssue) {
		this.maxCountIssue = maxCountIssue;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

}
