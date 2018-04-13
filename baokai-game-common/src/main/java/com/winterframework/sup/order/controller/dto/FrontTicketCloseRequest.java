package com.winterframework.sup.order.controller.dto;

import com.winterframework.firefrog.common.base.BaseRequest;

public class FrontTicketCloseRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;
	
	private String platformCode;
	
	private Long ticketId;

	private Long userId;

	private Long estimateScore;

	private String estimateMsg;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getEstimateScore() {
		return estimateScore;
	}

	public void setEstimateScore(Long estimateScore) {
		this.estimateScore = estimateScore;
	}

	public String getEstimateMsg() {
		return estimateMsg;
	}

	public void setEstimateMsg(String estimateMsg) {
		this.estimateMsg = estimateMsg;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

}
