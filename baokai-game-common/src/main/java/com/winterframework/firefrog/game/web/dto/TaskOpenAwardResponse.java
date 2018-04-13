package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName TaskOpenAwardResponse 
* @Description task项目开奖接口返回参数 
* @author  hugh
* @date 2014年8月25日 上午11:11:45 
*  
*/
public class TaskOpenAwardResponse implements Serializable {

	private static final long serialVersionUID = -4820527908526103362L;

	@NotNull
	private int result = 0; //0成功 非0失败
	
	private String message; //消息  （一般失败，返回失败原因）
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
