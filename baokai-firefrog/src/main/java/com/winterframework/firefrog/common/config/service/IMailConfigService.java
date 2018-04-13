package com.winterframework.firefrog.common.config.service;

import java.util.List;

import com.winterframework.firefrog.common.config.entity.MailConfigEnitiy;

public interface IMailConfigService {
	public List<MailConfigEnitiy> GetAllMailConfig ();
	public List<MailConfigEnitiy> GetEmailByID(String account);
	public void UpdateEmailByID(MailConfigEnitiy updateMailConfig);
	public void InsertEmail(MailConfigEnitiy InsertEmail);
	public void DeleteEmailById(String deleteAccount);
}
