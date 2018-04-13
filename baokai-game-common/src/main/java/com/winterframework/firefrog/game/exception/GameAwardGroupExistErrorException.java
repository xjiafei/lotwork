package com.winterframework.firefrog.game.exception;

/**
 * 
* @ClassName: GameAwardGroupExistErrorException 
* @Description: 新增奖金组异常
* @author Richard
* @date 2013-11-7 上午11:56:35 
*
 */
public class GameAwardGroupExistErrorException extends RuntimeException {

	private static final long serialVersionUID = 6249158835809275309L;

	public final static Long CODE = 202006L;

	public final static String MSG = "存在相同奖金组名称";

	public GameAwardGroupExistErrorException() {
		super(MSG);
	}

	public GameAwardGroupExistErrorException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}
}
