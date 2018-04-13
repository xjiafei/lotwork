package com.winterframework.firefrog.help.web.dto;

public class HelpFeedBackRequest {
	
	private long uId;
	
	private long helpId;
	
	private long isSolved;
	
	private long reasonId;
	
	private String reason;

	public long getuId() {
		return uId;
	}

	public void setuId(long uId) {
		this.uId = uId;
	}

	public long getHelpId() {
		return helpId;
	}

	public void setHelpId(long helpId) {
		this.helpId = helpId;
	}

	public long getIsSolved() {
		return isSolved;
	}

	public void setIsSolved(long isSolved) {
		this.isSolved = isSolved;
	}

	public long getReasonId() {
		return reasonId;
	}

	public void setReasonId(long reasonId) {
		this.reasonId = reasonId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
