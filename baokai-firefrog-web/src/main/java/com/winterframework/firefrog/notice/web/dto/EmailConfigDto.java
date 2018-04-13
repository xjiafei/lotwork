package com.winterframework.firefrog.notice.web.dto;

import java.io.Serializable;

public class EmailConfigDto implements Serializable{
	
	private String sendmethod;

	private String smtpserver;

	private String port;

	private String sender;

	private String account;

	private String password;
	
	private String sendsign;

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
	
	public String getSendsign() {
		return sendsign;
	}

	public void setSendsign(String sendsign) {
		this.sendsign = sendsign;
	}
}
