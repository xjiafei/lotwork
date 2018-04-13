package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

public class LotteryResultStruc implements Serializable {
	private static final long serialVersionUID = -1810852323093377270L;

	private String account;

	private Date rewardTime;

	private String rewardName;

	private Integer channel;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(Date rewardTime) {
		this.rewardTime = rewardTime;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

}
