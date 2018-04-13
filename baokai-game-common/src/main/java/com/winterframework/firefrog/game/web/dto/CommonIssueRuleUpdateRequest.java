package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class CommonIssueRuleUpdateRequest implements Serializable {

	private static final long serialVersionUID = -4975373561714269943L;

	@NotNull
	private Long lotteryId;

	@NotNull
	private Integer scheduleStopTime;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getScheduleStopTime() {
		return scheduleStopTime;
	}

	public void setScheduleStopTime(Integer scheduleStopTime) {
		this.scheduleStopTime = scheduleStopTime;
	}
}
