package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameIssueListDownLoadRequest implements Serializable {

	private static final long serialVersionUID = 1429388665080304524L;

	/** 彩种 */
	private Long lotteryIdDown;
	/** 状态:1 过去奖期 2 未来奖期 3 未来奖期（包括待审核） */

	private Integer queryTypeDown;

	private Long showStartTimeDown;

	private Long showEndTimeDown;

	public Long getLotteryIdDown() {
		return lotteryIdDown;
	}

	public void setLotteryIdDown(Long lotteryIdDown) {
		this.lotteryIdDown = lotteryIdDown;
	}

	public Integer getQueryTypeDown() {
		return queryTypeDown;
	}

	public void setQueryTypeDown(Integer queryTypeDown) {
		this.queryTypeDown = queryTypeDown;
	}

	public Long getShowStartTimeDown() {
		return showStartTimeDown;
	}

	public void setShowStartTimeDown(Long showStartTimeDown) {
		this.showStartTimeDown = showStartTimeDown;
	}

	public Long getShowEndTimeDown() {
		return showEndTimeDown;
	}

	public void setShowEndTimeDown(Long showEndTimeDown) {
		this.showEndTimeDown = showEndTimeDown;
	}
}
