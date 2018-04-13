/**   
* @Title: UserFundRequestDTO.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-6-28 下午5:58:38 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: UserFundRequestDTO 
* @Description: 用户资金信息请求DTO
* @author david
* @date 2013-6-28 下午5:58:38 
*  
*/
public class UserFundRequestDTO {
	@NotNull
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
