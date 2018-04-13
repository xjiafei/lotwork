package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GameOrderRequestDTO implements Serializable {

	private static final long serialVersionUID = 8344029477891865706L;
	private String gameType;//对应lotteryId
	private List<BetDetailStrucDTO> balls;
	private List<GamePlanParm> orders;
	/**是否追号 1追号，默认0*/
	private Integer isTrace;
	private Integer traceStopValue;
	private Integer traceWinStop;
	private String amount;
	private String channel;
	private String version;
	private Long activityType = 0l;
	private Long awardMode;
	private Long isWap = 0l;
	/**奬金組ID*/
	private Long awardGroupId;

	public Long getAwardMode() {
		return awardMode;
	}
	public void setAwardMode(Long awardMode) {
		this.awardMode = awardMode;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	/** 
	* //封锁变价时是否是第一次提交0：不是，1：是
	*/ 
	private Integer isFirstSubmit;
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public List<BetDetailStrucDTO> getBalls() {
		return balls;
	}
	public void setBalls(List<BetDetailStrucDTO> balls) {
		this.balls = balls;
	}
	public List<GamePlanParm> getOrders() {
		return orders;
	}
	public void setOrders(List<GamePlanParm> orders) {
		this.orders = orders;
	}
	/**
	 * 取得是否追号。
	 * @return 1:追号，默认0
	 */
	public Integer getIsTrace() {
		return isTrace;
	}
	/**
	 * 設定是否追号。
	 * @param isTrace 1:追号，默认0
	 */
	public void setIsTrace(Integer isTrace) {
		this.isTrace = isTrace;
	}
	public Integer getTraceStopValue() {
		return traceStopValue;
	}
	public void setTraceStopValue(Integer traceStopValue) {
		this.traceStopValue = traceStopValue;
	}
	public Integer getTraceWinStop() {
		return traceWinStop;
	}
	public void setTraceWinStop(Integer traceWinStop) {
		this.traceWinStop = traceWinStop;
	}

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	public Long getIsWap() {
		return isWap;
	}
	public void setIsWap(Long isWap) {
		this.isWap = isWap;
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
