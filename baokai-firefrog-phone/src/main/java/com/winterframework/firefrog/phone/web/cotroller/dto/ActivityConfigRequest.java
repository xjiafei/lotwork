package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


/** 
* @ClassName ActivityConfigRequest 
* @Description 活动配置
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityConfigRequest implements Serializable {

	private static final long serialVersionUID = 51874940978504543L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
