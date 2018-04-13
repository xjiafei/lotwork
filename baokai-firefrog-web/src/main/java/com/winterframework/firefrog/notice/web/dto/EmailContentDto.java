package com.winterframework.firefrog.notice.web.dto;

public class EmailContentDto {

	private String sendmethod;

	private String smtpserver;

	private String port;

	private String sender;

	private String account;

	private String password;

	private String rcvEmail;

	private String title;

	private String content;

	public String getSendmethod() {
		return sendmethod;
	}

	public void setSendmethod(String sendmethod) {
		this.sendmethod = sendmethod;
	}

	public String getSmtpserver() {
		return smtpserver;
	}

	public void setSmtpserver(String smtpserver) {
		this.smtpserver = smtpserver;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRcvEmail() {
		return rcvEmail;
	}

	public void setRcvEmail(String rcvEmail) {
		this.rcvEmail = rcvEmail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
