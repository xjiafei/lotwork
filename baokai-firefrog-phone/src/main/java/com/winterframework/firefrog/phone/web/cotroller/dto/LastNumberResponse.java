package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class LastNumberResponse implements Serializable {
	private String issue; 
	private String issueCode;
	private String codes;
	public String getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}

	
}
