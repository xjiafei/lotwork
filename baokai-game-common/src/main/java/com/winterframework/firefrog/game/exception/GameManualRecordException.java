package com.winterframework.firefrog.game.exception;

public class GameManualRecordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GameManualRecordException() {
		super();
	}

	public GameManualRecordException(String message) {
		super(message);
	}

	public Long getCode() {
		if(getMessage()!=null){
			return Long.parseLong(getMessage());
		}else{
			return null;
		}
		
	}
	
	public GameManualRecordException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameManualRecordException(Throwable cause) {
		super(cause);
	}
}