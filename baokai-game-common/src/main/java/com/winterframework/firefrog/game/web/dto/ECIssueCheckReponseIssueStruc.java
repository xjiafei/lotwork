package com.winterframework.firefrog.game.web.dto;

public class ECIssueCheckReponseIssueStruc {
   
	private String lottery;
	
	private String issue;
	
	private String drawTime;
	
	private String saleCloseTime;

	public String getLottery() {
		return lottery;
	}

	public void setLottery(String lottery) {
		this.lottery = lottery;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(String drawTime) {
		this.drawTime = drawTime;
	}

	public String getSaleCloseTime() {
		return saleCloseTime;
	}

	public void setSaleCloseTime(String saleCloseTime) {
		this.saleCloseTime = saleCloseTime;
	}
	
}
