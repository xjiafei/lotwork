package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;


/** 
* @ClassName UserInfoRequest 
* @Description 请求用户信息
* @author  david
* @date 
*  
*/
public class UserInfoRequest implements Serializable {

	private static final long serialVersionUID = 7957144425140963772L;
	
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
