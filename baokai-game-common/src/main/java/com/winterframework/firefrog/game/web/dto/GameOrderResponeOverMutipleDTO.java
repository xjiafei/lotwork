package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class GameOrderResponeOverMutipleDTO implements Serializable {
	private static final long serialVersionUID = 6728919732907732519L;
	private String betDetail;//投注内容
	private String betTypeCode;
	private Integer multiple;//超过倍数
	private Long gameIssueCode;//奖期
	private String webIssueCode;
	private String betMethod;//玩法
	private String moneyunit;
	
	public String getMoneyunit() {
		return moneyunit;
	}
	public void setMoneyunit(String moneyunit) {
		this.moneyunit = moneyunit;
	}
	public String getBetMethod() {
		return betMethod;
	}
	public void setBetMethod(String betMethod) {
		this.betMethod = betMethod;
	}
	public String getBetDetail() {
		return betDetail;
	}
	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}
	public String getBetTypeCode() {
		return betTypeCode;
	}
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
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
	
}
