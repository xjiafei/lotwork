package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: UserCenterReportsingleRequest 
* @Description: 用户中心盈亏报表简单条件请求
* @author david 
* @date 2013-9-17 下午2:54:07 
*  
*/
public class CurrentUserCenterReportRequest implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	@NotNull
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
