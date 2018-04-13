package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserBizSwitchRequest implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -327893887322326123L; 
	private Long userId; 
	private Integer type;	//业务开关类型	
	private Integer settMode;	//业务开关设置模式
	private Integer status;	//业务开关状态
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSettMode() {
		return settMode;
	}
	public void setSettMode(Integer settMode) {
		this.settMode = settMode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
