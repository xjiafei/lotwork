package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


/** 
* @ClassName ActivityQueryUserUpdateRequest 
* @Description 用户升级系统 
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityUserUpdateRequest implements Serializable {

	private static final long serialVersionUID = 51874940978504543L;

	/** 彩种 */
	@NotNull
	private Long userId;
	
	private Long type;//类型  查询时不需要

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
	
	

}
