package com.winterframework.firefrog.game.exception;

public class GameIssueTemplateSalePeriodTimeShouledBiggerThenScheduleStopTimeException extends Exception {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 203011L;

	public final static String MSG = "等待开奖时间不能超过开奖周期";

	public GameIssueTemplateSalePeriodTimeShouledBiggerThenScheduleStopTimeException() {
		super(MSG);
	}

	public GameIssueTemplateSalePeriodTimeShouledBiggerThenScheduleStopTimeException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
