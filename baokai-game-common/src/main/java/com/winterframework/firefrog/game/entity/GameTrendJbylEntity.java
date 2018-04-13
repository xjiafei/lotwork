package com.winterframework.firefrog.game.entity;

import java.io.Serializable;

/** 
* @ClassName: GameTrendJbylEntity 
* @Description: 走势图数据实体bean 
* @author Denny 
* @date 2014-3-27 下午1:50:52 
*  
*/
public class GameTrendJbylEntity implements Serializable{

	private static final long serialVersionUID = -1846723731735704796L;

	private Long lotteryId;
	private Long issueCode;
    private Long webIssueCode;
    private Integer gameGroupCode;
    private Integer gameSetCode;
    private Integer betMethodCode;
    private String value;
	private TrendType trendType;
	
	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public Long getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(Long webIssueCode) {
		this.webIssueCode = webIssueCode;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TrendType getTrendType() {
		return trendType;
	}

	public void setTrendType(TrendType trendType) {
		this.trendType = trendType;
	}
}
