package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserBizSwitchResponse implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 3641699906852747102L;
	private Integer status;	//业务开关状态

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
