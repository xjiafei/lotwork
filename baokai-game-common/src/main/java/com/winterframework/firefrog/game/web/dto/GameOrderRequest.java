package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

public class GameOrderRequest implements Serializable {

	private static final long serialVersionUID = 8344029477891865706L;

	@NotNull
	private Long lotteryid;
	@NotNull
	private Long issueCode;
	@NotNull
	private List<BetDetailStruc> betDetailStruc;
	private Long userIp;
	private Long serverIp;
	@NotNull
	private Long saleTime;
	@NotNull
	private Long packageAmount;
	
	private Integer channelId;
	private String channelVersion;
	private Long activityType = 0l;
	/**奬金組ID*/
	private Long awardGroupId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelVersion() {
		return channelVersion;
	}

	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}

	//封锁变价时是否是第一次提交0：不是，1：是
	private Integer isFirstSubmit;
	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getUserIp() {
		return userIp;
	}

	public void setUserIp(Long userIp) {
		this.userIp = userIp;
	}

	public Long getServerIp() {
		return serverIp;
	}

	public void setServerIp(Long serverIp) {
		this.serverIp = serverIp;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public List<BetDetailStruc> getBetDetailStruc() {
		return betDetailStruc;
	}

	public void setBetDetailStruc(List<BetDetailStruc> betDetailStruc) {
		this.betDetailStruc = betDetailStruc;
	}

	public Long getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Long saleTime) {
		this.saleTime = saleTime;
	}

	public Long getPackageAmount() {
		return packageAmount;
	}

	public void setPackageAmount(Long packageAmount) {
		this.packageAmount = packageAmount;
	}

	public Integer getIsFirstSubmit() {
		return isFirstSubmit;
	}

	public void setIsFirstSubmit(Integer isFirstSubmit) {
		this.isFirstSubmit = isFirstSubmit;
	}

	public Long getActivityType() {
		return activityType;
	}

	public void setActivityType(Long activityType) {
		this.activityType = activityType;
	}

	/**
	 * 取得奬金組ID。
	 * @return 
	 */
	public Long getAwardGroupId() {
		return awardGroupId;
	}

	/**
	 * 設定奬金組ID。
	 * @param awardGroupId 
	 */
	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}
	
}
