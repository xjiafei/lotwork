package com.winterframework.firefrog.common.config.dao;
import java.util.List;


import com.winterframework.firefrog.common.config.entity.MailConfigEnitiy;


public interface IMailConfigDao {

	public List<MailConfigEnitiy> GetAllMailConfig ();
	public List<MailConfigEnitiy> GetEMailByID (String account);
	public void UpdateEmailByID(MailConfigEnitiy account);
	public void InsertEmail(MailConfigEnitiy insertEmail);
	public void DeleteEmailById(String deleteAccount);
}








