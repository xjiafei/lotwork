package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName GameRiskResponse 
* @Description 审核奖期 
* @author  hugh
* @date 2014年4月22日 下午1:43:41 
*  
*/
public class GameRiskResponse implements Serializable {

	private static final long serialVersionUID = 7801778799498431320L;

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
