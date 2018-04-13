package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


/** 
* @ClassName ActivityUserAwardRequest 
* @Description 用户获奖记录
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityUserAwardRequest implements Serializable {

	private static final long serialVersionUID = 51874940978504543L;

	private Long userId;
	


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	

}
