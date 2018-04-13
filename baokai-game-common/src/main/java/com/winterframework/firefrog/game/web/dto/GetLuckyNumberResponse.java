package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;


/** 
* @ClassName GetLuckyRequest 
* @Description 用户抽奖次数返回
* @author  david
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class GetLuckyNumberResponse implements Serializable {

	private static final long serialVersionUID = 51874940978504543L;

	private Long number;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
	
}
