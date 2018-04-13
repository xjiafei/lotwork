package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class RedEnvelopeStruc implements Serializable {

	private static final long serialVersionUID = -1477183186264315235L;
	
	private String betDate;
	
	private String account;
	
	private Long betAmount;
	
	private Long award;
	
	private Integer status;
	
	private Integer channel;

	public String getBetDate() {
		return betDate;
	}

	public void setBetDate(String betDate) {
		this.betDate = betDate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Long betAmount) {
		this.betAmount = betAmount;
	}

	public Long getAward() {
		return award;
	}

	public void setAward(Long award) {
		this.award = award;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}
}
