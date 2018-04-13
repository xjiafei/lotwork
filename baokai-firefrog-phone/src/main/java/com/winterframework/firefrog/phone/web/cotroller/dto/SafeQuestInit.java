package com.winterframework.firefrog.phone.web.cotroller.dto;

public class SafeQuestInit {

	private Integer qid;
	private String question;
	private Integer isUsed;//0表示没设定过  1 表示有设定过
	public Integer getQid() {
		return qid;
	}
	public void setQid(Integer qid) {
		this.qid = qid;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	
}
