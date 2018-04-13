package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName TaskOpenAwardRequest 
* @Description task项目开奖接口请求参数 
* @author  hugh
* @date 2014年8月25日 上午11:11:12 
*  
*/
public class TaskOpenAwardRequest implements Serializable{
 
	private static final long serialVersionUID = 4159864978483459197L;
	
	@NotNull
	private Long orderId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	
	
}
