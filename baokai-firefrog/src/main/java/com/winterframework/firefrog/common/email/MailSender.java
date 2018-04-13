package com.winterframework.firefrog.common.email;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.winterframework.firefrog.common.config.entity.MailConfigEnitiy;
import com.winterframework.firefrog.notice.web.dto.EmailContentDto;
import com.winterframework.modules.spring.exetend.PropertyConfig;

public abstract class MailSender implements IMailSender {
	private final static Logger logger = Logger.getLogger("MailSender");

	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	@PropertyConfig(value = "mail.smtp.host")
	protected String mailServerHost;

	@PropertyConfig(value = "mail.port")
	protected String mailServerPort;

	@PropertyConfig(value = "mail.fromAddress")
	protected String fromAddress;

	@PropertyConfig(value = "mail.fromAddress")
	protected String userName;

	@PropertyConfig(value = "mail.password")
	protected String password;

	@PropertyConfig(value = "mail.ssl")
	protected String auth;
	protected String sendSign;

	static int mailindex = 0;
	public static synchronized int getCount(int nSize) {
		mailindex++;
		if (mailindex >= nSize)
			mailindex = 0 ;
		return mailindex;
	}
	
	protected void configProperties() {

	}
	protected void configMail() {

	}
	
	protected void configMail(MailConfigEnitiy mailEntity) {

	}
	 
	public String getSendSign() {
		return sendSign;
	}

	public void setSendSign(String sendSign) {
		this.sendSign = sendSign;
	}

	public Message createMessage() throws Exception {
		try {
			Message mailMessage = new MimeMessage(getMailSession());

			Address from = new InternetAddress(fromAddress);
			String nickName = "";
			try {
				nickName = javax.mail.internet.MimeUtility.encodeText(sendSign);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			logger.error("nickName----"+sendSign+"|"+nickName+"|"+fromAddress+"|"+from);
			mailMessage.setFrom(new InternetAddress(nickName + " <" + from + ">"));
			//mailMessage.setFrom(from);
			mailMessage.setSentDate(new Date());
			return mailMessage;
		} catch (MessagingException ex) {
			logger.error("create mail message error.", ex);
			throw ex;
		}
	}

	public void sendMail(EmailInfo email) throws Exception {
		configProperties();
		configMail ();
		if (email.getFromAdress() != null) {
			
			fromAddress = email.getFromAdress();
		}
		Message mailMessage = createMessage();
		if (StringUtils.isBlank(email.getAddress())) {
			return;
		}
		Address to = new InternetAddress(email.getAddress());
		mailMessage.setRecipient(Message.RecipientType.TO, to);
		mailMessage.setSubject(email.getTitle());
		Multipart mainPart = new MimeMultipart("related");
		BodyPart html = new MimeBodyPart();
		html.setContent(email.getContent(), CONTENT_TYPE);
		mainPart.addBodyPart(html);
		mailMessage.setContent(mainPart);
		Session session = getMailSession();
		Transport trans = session.getTransport(new InternetAddress());
	
		trans.connect(mailServerHost, Integer.valueOf(mailServerPort), userName, password);
		trans.sendMessage(mailMessage, mailMessage.getAllRecipients());
		trans.close();
		
	}
	
	//EMail測試用的
		public Boolean testsendMail(EmailContentDto mail) throws Exception {
			MailConfigEnitiy mailEntity =new MailConfigEnitiy();
				mailEntity.SetSendmethod(mail.getSendmethod());
				mailEntity.SetSmtpServer(mail.getSmtpserver());
				mailEntity.SetPort(mail.getPort());
				mailEntity.SetSender(mail.getSender());
				mailEntity.SetAccount(mail.getAccount());
				mailEntity.SetPassword(mail.getPassword());
			
			EmailInfo email = new EmailInfo();
				email.setAddress(mail.getRcvEmail());
				email.setContent(mail.getContent());
				email.setTitle(mail.getTitle());
				email.setFromAdress(mail.getSender());
			logger.info("start_configproperties");
			configProperties();
			logger.info("start_configMail");
			configMail (mailEntity);
			if (email.getFromAdress() != null) {
				
				fromAddress = email.getFromAdress();
			}
			Message mailMessage = createMessage();
			if (StringUtils.isBlank(email.getAddress())) {
				return false;
			}
			Address to = new InternetAddress(email.getAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			//設標題
			mailMessage.setSubject(email.getTitle());
			//設內容
			Multipart mainPart = new MimeMultipart("related");
			BodyPart html = new MimeBodyPart();
			html.setContent(email.getContent(), CONTENT_TYPE);
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);
			Session session = getMailSession();
			Transport trans = session.getTransport(new InternetAddress());
			logger.info("start_connect");
			trans.connect(mailServerHost, Integer.valueOf(mailServerPort), userName, password);
			logger.info("start_sendMessage");
			trans.sendMessage(mailMessage, mailMessage.getAllRecipients());
			trans.close();
			logger.info("end_all");
			return true;
			
		}

	/** 
	    * 读取文件 
	    *  
	    * @param file 
	    *            文件路径 
	    * @return 返回二进制数组 
	    */
	public byte[] readFile(String file) {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			int bytesRead;
			byte buffer[] = new byte[1024 * 1024];
			while ((bytesRead = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);
				Arrays.fill(buffer, (byte) 0);
			}
		} catch (IOException e1) {
			logger.error("read file error.", e1);
		} finally {
			try {
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				logger.error("close error.", e);
			}

			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				logger.error("close error.", e);
			}
		}
		return bos.toByteArray();
	}

	protected Session getMailSession() {
		Authenticator authenticator = null;
		if ("true".equals(auth)) {
			authenticator = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};
		}
		Properties pro = getProperties();
		Session mailSession = Session.getInstance(pro, authenticator);

		return mailSession;
	}

	protected abstract Properties getProperties();
}
