/**   
* @Title: EmailTemplateDto.java 
* @Package com.winterframework.firefrog.config.web.controller.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-24 上午10:19:05 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.web.dto;

/** 
* @ClassName: EmailTemplateDto 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-24 上午10:19:05 
*  
*/
public class EmailTemplateDto {

	private String sendMethod;

	private String smtpServer;

	private Long smtpPort;

	private String sendEmail;

	private String sendSign;

	private Integer smtpAuth;

	private String userName;

	private String passwd;

	public String getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public Long getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Long smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getSendSign() {
		return sendSign;
	}

	public void setSendSign(String sendSign) {
		this.sendSign = sendSign;
	}

	public Integer getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(Integer smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
