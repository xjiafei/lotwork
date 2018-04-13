package com.winterframework.firefrog.advert.web.dto;

/** 
* @ClassName ActivityUserUpdateActionResponse 
* @Description 更新用户update情况 
* @author  hugh
* @date 2014年11月28日 上午11:55:57 
*  
*/
public class ActivityUserUpdateActionResponse {

	private Long status;
	
	private String message;
	

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
