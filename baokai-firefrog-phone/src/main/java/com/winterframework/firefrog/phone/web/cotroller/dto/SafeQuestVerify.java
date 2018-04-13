package com.winterframework.firefrog.phone.web.cotroller.dto;

public class SafeQuestVerify {

	private Integer qid	;//问题流水号
	private String qpwd	;//问题答案
	public Integer getQid() {
		return qid;
	}
	public void setQid(Integer qid) {
		this.qid = qid;
	}
	public String getQpwd() {
		return qpwd;
	}
	public void setQpwd(String qpwd) {
		this.qpwd = qpwd;
	}
	
}
