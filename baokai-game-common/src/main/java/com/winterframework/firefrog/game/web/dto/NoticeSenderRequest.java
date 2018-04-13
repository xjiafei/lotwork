package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Map;

public class NoticeSenderRequest implements Serializable {

	private static final long serialVersionUID = -6120075166773481047L;

	private Long userId;

	private String account;

	private Long taskId;

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
