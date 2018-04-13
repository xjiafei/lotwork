/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.common.config.dao.vo;
   
import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class MailConfig extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 259375108585605752L;


	//columns START
	private String  sendmethod;
	private String  smtpserver;
	private String 	mailport;
	private String  sender;
	private String  account;
	private String  password;

	//columns END

	public MailConfig() {
	}
	public MailConfig (Long id) {
		this.id = id;
	}

	
	
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



	public String getMailport() {
		return mailport;
	}



	public void setMailport(String mailport) {
		this.mailport = mailport;
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



	
}
