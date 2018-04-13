package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GameOrderDiceRequestDTO implements Serializable {

	private static final long serialVersionUID = 8344029477891865706L;
	private String gameType;//对应lotteryId
	private List<BetDetailStrucDTO> balls;
	private List<GamePlanParm> orders;
	private Integer isTrace;// 是否追号 1追号，默认0
	private String amount;
	private Integer multiple;
	private Integer trace;
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

	public Integer getIsTrace() {
		return isTrace;
	}
	public void setIsTrace(Integer isTrace) {
		this.isTrace = isTrace;
	}
	public Integer getIsFirstSubmit() {
		return isFirstSubmit;
	}
	public void setIsFirstSubmit(Integer isFirstSubmit) {
		this.isFirstSubmit = isFirstSubmit;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public Integer getTrace() {
		return trace;
	}
	public void setTrace(Integer trace) {
		this.trace = trace;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
