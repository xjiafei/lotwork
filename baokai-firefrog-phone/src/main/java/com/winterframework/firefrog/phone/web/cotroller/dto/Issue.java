package com.winterframework.firefrog.phone.web.cotroller.dto;


public class Issue {
	
	private String issue;//	当前奖期
	private String salestart;//	开始销售时间
	private String saleend;//结束销售时间
	private String nextIssue;//	下期奖期资料;
	private Long issueCode;
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getSalestart() {
		return salestart;
	}
	public void setSalestart(String salestart) {
		this.salestart = salestart;
	}
	public String getSaleend() {
		return saleend;
	}
	public void setSaleend(String saleend) {
		this.saleend = saleend;
	}
	public String getNextIssue() {
		return nextIssue;
	}
	public void setNextIssue(String nextIssue) {
		this.nextIssue = nextIssue;
	}
	
	

}
