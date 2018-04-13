package com.winterframework.sup.order.controller.dto;

import com.winterframework.firefrog.common.base.BaseRequest;

public class FrontTicketDetailRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	private String platformCode;

	private String account;

	private Long userId;

	private String lvl;

	public Long platformUserId;

	private Long ticketId;

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getPlatformUserId() {
		return platformUserId;
	}

	public void setPlatformUserId(Long platformUserId) {
		this.platformUserId = platformUserId;
	}

}
