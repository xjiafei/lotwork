package com.winterframework.firefrog.game.exception;

/**
 * 当前彩种已停售
 * @author Pogi.Lin
 */
public class GameSeriesStatusErrorException extends RuntimeException {

	private static final long serialVersionUID = -6826891733785691434L;

	public final static Long CODE = 201006L;

	public final static String MSG = "当前彩种已停售";

	public GameSeriesStatusErrorException() {
		super(MSG);
	}

	public GameSeriesStatusErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
