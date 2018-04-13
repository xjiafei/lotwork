package com.winterframework.firefrog.game.exception;

/**
 * 方案编号，订单编号或追号编号已经存在
 * @author Pogi.Lin
 */
public class GameOrderOrPlanCodeExistErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 2030003L;

	public final static String MSG = "方案编号，订单编号或追号编号已经存在";

	public GameOrderOrPlanCodeExistErrorException() {
		super(MSG);
	}

	public GameOrderOrPlanCodeExistErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
