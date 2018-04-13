package com.winterframework.firefrog.game.exception;

/** 
* @ClassName: GameIssueStatusErrorException 
* @Description: 投注方式暂停 暂停异常需要后台截取判断具体到是哪一个投注方式暂停
* @author david
* @date 2013-7-29 下午2:15:33 
*  
*/
public class GameBetMethodStatusStopException extends RuntimeException {

	private static final long serialVersionUID = -653485764121619615L;

	public final static Long CODE = 202008L;

	public final static String MSG = "投注方式暂停";
	
	private String strCode;

	public GameBetMethodStatusStopException() {
		super(MSG);
	}
	
	public GameBetMethodStatusStopException(String str) {
		this.strCode=str;
	}


	public GameBetMethodStatusStopException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return Long.valueOf(strCode+CODE);
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}
	
	
}
