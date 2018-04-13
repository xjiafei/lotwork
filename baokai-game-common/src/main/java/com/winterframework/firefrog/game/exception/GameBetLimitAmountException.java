package com.winterframework.firefrog.game.exception;

public class GameBetLimitAmountException extends RuntimeException {
	
	private static final long serialVersionUID = -6504400706927226130L;

	public final static Long CODE = 202009L;

	public final static String MSG = "您的投注金額超過上限，请调整！";

	public GameBetLimitAmountException() {
		super(MSG);
	}

	public GameBetLimitAmountException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
