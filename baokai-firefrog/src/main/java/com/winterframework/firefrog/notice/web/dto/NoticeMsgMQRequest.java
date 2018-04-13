package com.winterframework.firefrog.notice.web.dto;

import java.io.Serializable;
import java.util.Map;

public class NoticeMsgMQRequest implements Serializable {

	private static final long serialVersionUID = -1172787482592896324L;

	/** 
	* 用户id 
	*/
	private Long userId;
	/** 
	* 账号 
	*/
	private String account;
	/** 
	* 任务ID 
	*/
	private Long taskId;
	/** 
	* 参数列表 
	*/
	private Map<String, String> paramMap;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
}
