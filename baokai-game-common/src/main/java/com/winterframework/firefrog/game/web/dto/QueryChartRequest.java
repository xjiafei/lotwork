package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class QueryChartRequest implements Serializable { 

	/**
	 * 
	 */
	private static final long serialVersionUID = -6530241477335321394L;
	@NotNull
	private Long lotteryId;
	@NotNull
	private String gameMethod;
	@NotNull
	private Integer type; //1：periods 2:day
	private Integer periodsNum;
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getGameMethod() {
		return gameMethod;
	}
	public void setGameMethod(String gameMethod) {
		this.gameMethod = gameMethod;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPeriodsNum() {
		return periodsNum;
	}
	public void setPeriodsNum(Integer periodsNum) {
		this.periodsNum = periodsNum;
	}

	
}
