package com.winterframework.firefrog.common.config.entity;

public class MailConfigEnitiy {

	private String  sendmethod;
	private String  smtpserver;
	private String 	mailport;
	private String  sender;
	private String  account;
	private String  password;
	private String  sendsign;
	
	public MailConfigEnitiy()
	{
		
	}
	
	public void SetSendmethod (String sender)
	{
		this.sendmethod = sender;
	}
	public String GetSendmethod ()
	{
		return this.sendmethod;
	}
	public void SetSmtpServer(String smtp)
	{
		this.smtpserver = smtp;
	}
	public String GetSmtpServer()
	{
		return this.smtpserver;
	}
	public void SetPort (String port)
	{
		this.mailport = port;
	}
	public String GetPort()
	{
		return this.mailport;
	}
	public void SetSender (String sender)
	{
		this.sender = sender;
	}
	public String GetSender()
	{
		return this.sender;
	}
	public void SetAccount (String account)
	{
		this.account = account;
	}
	public String GetAccount ()
	{
		return this.account;
	}
	public void SetPassword (String password)
	{
		this.password = password;
	}
	public String GetPassword ()
	{
		return this.password;
	}
	public void SetSendsign (String sendsign)
	{
		this.sendsign = sendsign;
	}
	
	public String GetSendsign ()
	{
		return this.sendsign;
	}
	
}
