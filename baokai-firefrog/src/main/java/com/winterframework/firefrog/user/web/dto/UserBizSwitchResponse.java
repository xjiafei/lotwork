package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.config.web.controller.dto.RegisterLoginConfigDto;
import com.winterframework.firefrog.user.web.controller.game.LoginGameGroup;

public class UserBizSwitchResponse implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -7320882872773289794L;
 
	private Integer status;	//业务开关状态

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
