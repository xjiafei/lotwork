package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GameOrderResponseDTO implements Serializable {
	private static final long serialVersionUID = 9138546628387278910L;
	private Long totalAmount;
	private String lotteryName;
	private List<GameSlipResponseDTO> slipResonseDTOList;
	private Long gameIssueCode;
	private String webIssueCode;
	private List<GamePlanParm> gamePlanParm;
	private Integer isTrace;// 是否追号 1追号，默认0
	private Integer stopMode;
	private Integer stopParms;
	private String saleTime;
	private String orderCode;
	private Long orderId;
	private List<BetDetailStrucDTO> balls;
	/**奬金組ID*/
	private Long awardGroupId;
	
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public Long getGameIssueCode() {
		return gameIssueCode;
	}
	public void setGameIssueCode(Long gameIssueCode) {
		this.gameIssueCode = gameIssueCode;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public List<GameSlipResponseDTO> getSlipResonseDTOList() {
		return slipResonseDTOList;
	}
	public void setSlipResonseDTOList(List<GameSlipResponseDTO> slipResonseDTOList) {
		this.slipResonseDTOList = slipResonseDTOList;
	}
	public List<GamePlanParm> getGamePlanParm() {
		return gamePlanParm;
	}
	public void setGamePlanParm(List<GamePlanParm> gamePlanParm) {
		this.gamePlanParm = gamePlanParm;
	}
	public Integer getIsTrace() {
		return isTrace;
	}
	public void setIsTrace(Integer isTrace) {
		this.isTrace = isTrace;
	}
	public Integer getStopMode() {
		return stopMode;
	}
	public void setStopMode(Integer stopMode) {
		this.stopMode = stopMode;
	}
	public Integer getStopParms() {
		return stopParms;
	}
	public void setStopParms(Integer stopParms) {
		this.stopParms = stopParms;
	}
	public String getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public List<BetDetailStrucDTO> getBalls() {
		return balls;
	}
	public void setBalls(List<BetDetailStrucDTO> balls) {
		this.balls = balls;
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
