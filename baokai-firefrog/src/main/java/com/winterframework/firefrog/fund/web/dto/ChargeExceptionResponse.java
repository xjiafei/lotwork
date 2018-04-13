/**   
* @Title: ChargeExceptionResponse.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-8 下午6:54:17 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

/** 
* @ClassName: ChargeExceptionResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-8 下午6:54:17 
*  
*/
public class ChargeExceptionResponse implements Serializable {

	private static final long serialVersionUID = -5113410932087045438L;

	private String exceptionOrderNum;

	private Long status;

	private String errorMsg;

	private String key;

	public String getExceptionOrderNum() {
		return exceptionOrderNum;
	}

	public void setExceptionOrderNum(String exceptionOrderNum) {
		this.exceptionOrderNum = exceptionOrderNum;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
