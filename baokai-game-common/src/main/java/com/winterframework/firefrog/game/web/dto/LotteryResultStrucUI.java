package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.winterframework.modules.utils.DataUtils;

public class LotteryResultStrucUI implements Serializable {
	private static final long serialVersionUID = -1810852323093377270L;

	private String account;

	private String rewardTime;

	private String rewardName;

	private String channel;

	
	
	
	public LotteryResultStrucUI(LotteryResultStruc struc) {
		super();
		this.account = struc.getAccount();
		if(struc.getRewardTime()!=null){
			this.rewardTime = DateFormatUtils.format(struc.getRewardTime(), "yyyy-MM-dd HH:mm:ss");
		}else{
			this.rewardTime ="";
		}
		this.rewardName = struc.getRewardName();
		if(struc.getChannel()!= null && struc.getChannel().longValue() == 0){
			this.channel = "pc"; 
		}else if(struc.getChannel()!= null && struc.getChannel().longValue() == 1){
			this.channel = "手机";
		}else{
			this.channel = "";
		}
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(String rewardTime) {
		this.rewardTime = rewardTime;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
