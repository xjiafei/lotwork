package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class AllIssueDto implements Serializable{

	private static final long serialVersionUID = -5482355627378678908L;
	private String issue;
	private String saleend;
	private String issueCode;
	public String getIssueCode() {
		return issueCode;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getSaleend() {
		return saleend;
	}
	public void setSaleend(String saleend) {
		this.saleend = saleend;
	}
	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	} 
	
	
}
