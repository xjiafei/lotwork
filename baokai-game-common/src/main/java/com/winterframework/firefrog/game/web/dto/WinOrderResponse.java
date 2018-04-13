package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class WinOrderResponse implements Serializable{

	private static final long serialVersionUID = 75639465347611576L;
	private String projectId;
	private Long winMoney;
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public Long getWinMoney() {
		return winMoney;
	}
	public void setWinMoney(Long winMoney) {
		this.winMoney = winMoney;
	}
}
