package com.winterframework.firefrog.game.service.utils;

import java.io.Serializable;

/** 
* @ClassName: TrendJbylQueryParam 
* @Description: 遗漏数据查询参数Bean 
* @author Denny 
* @date 2014-4-11 下午2:24:27 
*  
*/
public class TrendJbylQueryParam implements Serializable {

	private static final long serialVersionUID = 4635778204792065894L;

	private Long lotteryId;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private Integer trendType;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Integer getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Integer getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	public Integer getTrendType() {
		return trendType;
	}

	public void setTrendType(Integer trendType) {
		this.trendType = trendType;
	}

}
