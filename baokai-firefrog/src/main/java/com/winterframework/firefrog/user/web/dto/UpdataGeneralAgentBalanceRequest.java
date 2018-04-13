/**   
* @Title: UpdataGeneralAgentBalanceRequest.java 
* @Package com.winterframework.firefrog.user.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-5-6 下午3:52:40 
* @version V1.0   
*/
package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: UpdataGeneralAgentBalanceRequest 
* @Description: 更新总代开户配额 
* @author Richard 
* @date 2013-5-6 下午3:52:40 
*  
*/
public class UpdataGeneralAgentBalanceRequest implements Serializable {

	private static final long serialVersionUID = 7842449083867910070L;

	@NotNull
	private Long userId;
	@NotNull
	private Long availableQuota;

	public UpdataGeneralAgentBalanceRequest() {

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAvailableQuota() {
		return availableQuota;
	}

	public void setAvailableQuota(Long availableQuota) {
		this.availableQuota = availableQuota;
	}

}
