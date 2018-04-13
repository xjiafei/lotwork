package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: QueryAssistActionRequest 
* @Description: 投注辅助图表查询请求
* @author Richard
* @date 2013-8-22 下午4:47:34 
*
 */
public class QueryAssistActionRequest implements Serializable {

	private static final long serialVersionUID = 2174536185056158600L;

	private Long lotteryid;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private Integer count;
	
	public QueryAssistActionRequest() {
		
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
