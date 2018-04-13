package com.winterframework.firefrog.notice.entity;

import java.util.Map;

/** 
*  向MQ中推送数据时的数据格式
*/
public class MQMsg {
	//用户id
	private Long userId;
	//用户名称
	private String userName;
	//对应的taskId
	private Long taskId;

	private Map<String, String> paramMap;
	
	public MQMsg(){
		
	}

	public MQMsg(Long userId, String userName, Long taskId, Map<String, String> paramMap) {
		this.userId = userId;
		this.userName = userName;
		this.taskId = taskId;
		this.paramMap = paramMap;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
}