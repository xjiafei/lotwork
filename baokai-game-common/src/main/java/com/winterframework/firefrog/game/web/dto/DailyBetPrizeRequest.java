package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class DailyBetPrizeRequest implements Serializable{

	
	private static final long serialVersionUID = 963477921777194254L;
	private Long userId;
	@NotNull
	private String betDate;
	@NotNull
	private Long money;
	
	private Integer channel; //领取渠道
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBetDate() {
		return betDate;
	}
	public void setBetDate(String betDate) {
		this.betDate = betDate;
	}
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
	
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	
	
}
