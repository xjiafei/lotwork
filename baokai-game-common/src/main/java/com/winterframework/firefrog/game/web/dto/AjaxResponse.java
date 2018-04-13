package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: AjaxReponse 
* @Description: ajax 请求返回respone
* @author david 
* @date 2013-9-26 上午12:37:36 
*  
*/
public class AjaxResponse {

	private Long status;
	
	private String message;
	
	private Object data;
	
	private String maxIssuesDay;
	private String maxIssueCode;

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

	public String getMaxIssuesDay() {
		return maxIssuesDay;
	}

	public void setMaxIssuesDay(String maxIssuesDay) {
		this.maxIssuesDay = maxIssuesDay;
	}

	public String getMaxIssueCode() {
		return maxIssueCode;
	}

	public void setMaxIssueCode(String maxIssueCode) {
		this.maxIssueCode = maxIssueCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
