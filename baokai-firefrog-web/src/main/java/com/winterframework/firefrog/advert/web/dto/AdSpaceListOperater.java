/**   
* @Title: AdSpaceListOperater.java 
* @Package com.winterframework.firefrog.advert.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-12 下午4:00:18 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.dto;

import java.util.List;

/** 
* @ClassName: AdSpaceListOperater 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-12 下午4:00:18 
*  
*/
public class AdSpaceListOperater {

	private Long status;
	private String message;
	private List<String> data;

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

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

}
