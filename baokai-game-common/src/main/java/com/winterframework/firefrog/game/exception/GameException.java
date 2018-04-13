package com.winterframework.firefrog.game.exception;

 
/**
 * 游戏异常
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月11日
 */
public class GameException extends RuntimeException {  
	private static final long serialVersionUID = 5067951220383014956L;
	private Long code = 0001L; 
	private String msg="游戏异常";
 
	public GameException() {
		super("游戏异常");
	}

	public GameException(Throwable cause) {
		super("游戏异常",cause);
	}
	public GameException(String msg) {
		super(msg);
	}
	public GameException(String msg,Throwable cause) {
		super(msg, cause);
	}
	public GameException(Long code,String msg) {
		this(msg, null);
		this.code=code;
	}
	public GameException(Long code,String msg,Throwable cause) {
		this(msg, cause);
		this.code=code;
	}
	public Long getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
}
