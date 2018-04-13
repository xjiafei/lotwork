package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: Bets 
* @Description:号码走势图表查询请求
* @author david
* @date 2013-8-23 下午2:35:42 
*
 */
public class QueryTrendChartRequest implements Serializable {

	private static final long serialVersionUID = 6210895184293763906L;

	@NotNull
	private Long lotteryId;
	@NotNull
	private Integer gameGroupCode;
	@NotNull
	private Integer type;
	private Long startDate;
	private Long endDate;
	private Long issue;
	@NotNull
	private Integer isGeneral;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Long getIssue() {
		return issue;
	}

	public void setIssue(Long issue) {
		this.issue = issue;
	}

	public Integer getIsGeneral() {
		return isGeneral;
	}

	public void setIsGeneral(Integer isGeneral) {
		this.isGeneral = isGeneral;
	}

}
