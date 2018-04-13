package com.winterframework.firefrog.help.web.dto;

public class HelpFeedBackRequest {
	
	private Long helpId;
	
	private Long isSolved;
	
	private Long reasonId;
	
	private String reason;

	public Long getHelpId() {
		return helpId;
	}

	public void setHelpId(Long helpId) {
		this.helpId = helpId;
	}

	public Long getIsSolved() {
		return isSolved;
	}

	public void setIsSolved(Long isSolved) {
		this.isSolved = isSolved;
	}

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}


}
