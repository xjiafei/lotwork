package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class TORiskResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4351888052750561892L;

	//0成功 //1失败
	private Long resultStatus;

	private String exceptionMessage;

	public Long getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Long resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
		
}
