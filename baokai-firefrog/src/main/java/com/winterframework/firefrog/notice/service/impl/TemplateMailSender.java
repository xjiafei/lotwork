/**   
* @Title: TemplateMailSender.java 
* @Package com.winterframework.firefrog.notice.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-25 上午11:56:13 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.service.impl;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.config.entity.MailConfigEnitiy;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.config.service.IMailConfigService;
import com.winterframework.firefrog.common.email.EmailInfo;
import com.winterframework.firefrog.common.email.MailSender;
import com.winterframework.firefrog.notice.web.dto.EmailTemplateDto;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: TemplateMailSender 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-25 上午11:56:13 
*  
*/
@Service("templateMailSender")
public class TemplateMailSender extends MailSender {

	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	protected String sendMethod;

	@Resource(name = "MailconfigServiceImpl")
	private IMailConfigService MailconfigService;
	
	protected void configProperties() {
		String str = configService.getConfigValueByKey("notice", "email");
		EmailTemplateDto dto = JsonMapper.nonEmptyMapper().fromJson(str, EmailTemplateDto.class);
		this.auth = dto.getSmtpAuth().intValue() == 1 ? "true" : "false";
		this.fromAddress = dto.getSendEmail();
		this.mailServerHost = dto.getSmtpServer();
		this.mailServerPort = String.valueOf(dto.getSmtpPort().longValue());
		this.password = dto.getPasswd();
		this.sendMethod = dto.getSendMethod();
		this.userName = dto.getUserName();
		this.sendSign=dto.getSendSign();
		
	}

	protected Properties getProperties() {
		Properties p = new Properties();
		if ("smtp".equals(sendMethod)) {
			p.put("mail.smtp.host", this.mailServerHost);
			p.put("mail.smtp.port", this.mailServerPort);
			p.put("mail.smtp.auth", auth);
		}
		return p;
	}
	protected void configMail(){
		List<MailConfigEnitiy> Listmailconfig = MailconfigService.GetAllMailConfig();
		if (Listmailconfig.isEmpty() == false){
			int nIndex =  getCount (Listmailconfig.size());
			MailConfigEnitiy mailEntity = Listmailconfig.get(nIndex);
			this.userName = mailEntity.GetAccount();
			this.password = mailEntity.GetPassword();
			this.fromAddress =  mailEntity.GetSender();
			this.mailServerPort = mailEntity.GetPort();
			this.sendMethod = mailEntity.GetSendmethod();
			this.mailServerHost = mailEntity.GetSmtpServer();
		}
		
	}
	
	protected void configMail(MailConfigEnitiy mailEntity){

		this.userName = mailEntity.GetAccount();
		this.password = mailEntity.GetPassword();
		this.fromAddress =  mailEntity.GetSender();
		this.mailServerPort = mailEntity.GetPort();
		this.sendMethod = mailEntity.GetSendmethod();
		this.mailServerHost = mailEntity.GetSmtpServer();
	
	
}
	
	 public static void main(String[] k){
		 try{
				Session session = getMailSession2();
				Transport trans = session.getTransport(new InternetAddress());
			
				trans.connect("smtp.188.com", Integer.valueOf("25"), "baokai@188.com", "zxzx2017@");
				//trans.sendMessage(mailMessage, mailMessage.getAllRecipients());
				trans.close();
				
			}
		 catch(Exception e){
			 System.out.println(e);
		 }
	 }
protected static Session getMailSession2() {
	Authenticator authenticator = null;
	if (true) {
		authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("baokai@188.com", "zxzx2017@");
			}
		};
	}
	Properties pro = getProperties2();
	Session mailSession = Session.getInstance(pro, authenticator);

	return mailSession;
}
protected static Properties getProperties2() {
	Properties p = new Properties();
	if (true) {
		p.put("mail.smtp.host", "smtp.188.com");
		p.put("mail.smtp.port", "25");
		p.put("mail.smtp.auth", "true");
	}
	return p;
}
}
