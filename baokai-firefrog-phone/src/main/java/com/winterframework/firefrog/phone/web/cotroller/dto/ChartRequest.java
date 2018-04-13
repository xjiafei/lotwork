package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ChartRequest implements Serializable {

 
	/**
	 * 
	 */
	private static final long serialVersionUID = 3684804445907191071L;
	private Long lotteryId;
	private String periodsType;
	private Integer periodsNum;
	private String gameMethod;
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getPeriodsType() {
		return periodsType;
	}
	public void setPeriodsType(String periodsType) {
		this.periodsType = periodsType;
	}
	public Integer getPeriodsNum() {
		return periodsNum;
	}
	public void setPeriodsNum(Integer periodsNum) {
		this.periodsNum = periodsNum;
	}
	public String getGameMethod() {
		return gameMethod;
	}
	public void setGameMethod(String gameMethod) {
		this.gameMethod = gameMethod;
	}

	
}
