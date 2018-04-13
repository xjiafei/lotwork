package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameSeriesListStruc implements Serializable {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = 8023398339306489912L;
	
	private Integer lotteryTypeCode;
	private String lotteryTypeName;
	private Integer lotterySeriesCode;
	private String lotterySeriesName;
	
	public GameSeriesListStruc() {

	}

	public Integer getLotteryTypeCode() {
		return lotteryTypeCode;
	}

	public void setLotteryTypeCode(Integer lotteryTypeCode) {
		this.lotteryTypeCode = lotteryTypeCode;
	}

	public String getLotteryTypeName() {
		return lotteryTypeName;
	}

	public void setLotteryTypeName(String lotteryTypeName) {
		this.lotteryTypeName = lotteryTypeName;
	}

	public Integer getLotterySeriesCode() {
		return lotterySeriesCode;
	}

	public void setLotterySeriesCode(Integer lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}

	public String getLotterySeriesName() {
		return lotterySeriesName;
	}

	public void setLotterySeriesName(String lotterySeriesName) {
		this.lotterySeriesName = lotterySeriesName;
	}
	
	
	
}
