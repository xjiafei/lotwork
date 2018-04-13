package com.winterframework.firefrog.notice.web.dto;

import com.winterframework.firefrog.common.config.entity.MailConfigEnitiy;

public class GetEmailConfigDto {

	
	public EmailConfigDto getEmail(MailConfigEnitiy mail){
		EmailConfigDto dao = new EmailConfigDto();
			dao.setSendmethod(mail.GetSendmethod());
			dao.setSmtpserver(mail.GetSmtpServer());
			dao.setPort(mail.GetPort());
			dao.setSender(mail.GetSender());
			dao.setAccount(mail.GetAccount());
			dao.setPassword(mail.GetPassword());
			dao.setSendsign(mail.GetSendsign());
			return dao;
	}
}
