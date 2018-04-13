package com.winterframework.firefrog.game.web.dto;


public class GamePlanBetOriginDataStruc {
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private Integer moneyMode;
	private Integer totbets;
	private Integer itemAmount;
	private Long totamount;
	private Integer multiple;
	private String betdetail;
	private Integer fileMode=new Integer(0);
	private Long issueCode;
	private Integer awardMode;	//奖金模式
	
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
	public Integer getMoneyMode() {
		return moneyMode;
	}
	public void setMoneyMode(Integer moneyMode) {
		this.moneyMode = moneyMode;
	}
	public Integer getTotbets() {
		return totbets;
	}
	public void setTotbets(Integer totbets) {
		this.totbets = totbets;
	}
	public Integer getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(Integer itemAmount) {
		this.itemAmount = itemAmount;
	}
	public Long getTotamount() {
		return totamount;
	}
	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public String getBetdetail() {
		return betdetail;
	}
	public void setBetdetail(String betdetail) {
		this.betdetail = betdetail;
	}
	public Integer getFileMode() {
		return fileMode;
	}
	public void setFileMode(Integer fileMode) {
		this.fileMode = fileMode;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public Integer getAwardMode() {
		return awardMode;
	}
	public void setAwardMode(Integer awardMode) {
		this.awardMode = awardMode;
	}
	
}
