package com.winterframework.sup.order.controller.dto;

import com.winterframework.firefrog.common.base.BaseRequest;

public class FrontTicketSaveRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	private String platformCode;
	
	private Long platformId;
	
	private Long ticketId;
	
	private Long userId;
	
	private Long platformUserId;

	private String account;
	
	private String lvl;

	private String mail;

	private Long questionParent;

	private Long question;

	private String title;

	private String description;

	private String files;

	private String vCode;
	
	private String appid;
	
	private Boolean isRegister = false;

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	
	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Long getQuestionParent() {
		return questionParent;
	}

	public void setQuestionParent(Long questionParent) {
		this.questionParent = questionParent;
	}

	public Long getQuestion() {
		return question;
	}

	public void setQuestion(Long question) {
		this.question = question;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getvCode() {
		return vCode;
	}

	public void setvCode(String vCode) {
		this.vCode = vCode;
	}

	public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	public Boolean getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Boolean isRegister) {
		this.isRegister = isRegister;
	}

	public Long getPlatformUserId() {
		return platformUserId;
	}

	public void setPlatformUserId(Long platformUserId) {
		this.platformUserId = platformUserId;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
}
