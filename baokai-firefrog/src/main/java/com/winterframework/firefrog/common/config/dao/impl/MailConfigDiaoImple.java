package com.winterframework.firefrog.common.config.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.config.dao.IMailConfigDao;
import com.winterframework.firefrog.common.config.dao.vo.MailConfig;
import com.winterframework.firefrog.common.config.dao.vo.VOConverter;
import com.winterframework.firefrog.common.config.entity.MailConfigEnitiy;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;


@Repository("MailDaoImpl")
public class MailConfigDiaoImple extends BaseIbatis3Dao<MailConfig> implements IMailConfigDao {
	
	public List<MailConfigEnitiy> GetAllMailConfig ()
	{
		List<MailConfigEnitiy> configList = new ArrayList<MailConfigEnitiy>();
		List <MailConfig> listmailVo = sqlSessionTemplate.selectList("getAllConfig");
		for (MailConfig vo : listmailVo) {
			configList.add(VOConverter.Mailconfg2MailConfigEntity(vo));
		}
		return configList;
	}
	public List<MailConfigEnitiy> GetEMailByID(String account) {
		List<MailConfigEnitiy> configList = new ArrayList<MailConfigEnitiy>();
		List <MailConfig> listmailVo = sqlSessionTemplate.selectList("getEmailById",account);
		for (MailConfig vo : listmailVo) {
			configList.add(VOConverter.Mailconfg2MailConfigEntity(vo));
		}
		return configList;
	}


	public void UpdateEmailByID(MailConfigEnitiy updateMailConfig) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("sendmethod", updateMailConfig.GetSendmethod());
		map.put("smtpserver", updateMailConfig.GetSmtpServer());
		map.put("mailport", updateMailConfig.GetPort());
		map.put("sender", updateMailConfig.GetSender());
		map.put("account", updateMailConfig.GetAccount());
		map.put("password", updateMailConfig.GetPassword());
		sqlSessionTemplate.update("updateMailConfigByID", map);
	}
	
	public void InsertEmail(MailConfigEnitiy InsertEmail) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("sendmethod", InsertEmail.GetSendmethod());
		map.put("smtpserver", InsertEmail.GetSmtpServer());
		map.put("mailport", InsertEmail.GetPort());
		map.put("sender", InsertEmail.GetSender());
		map.put("account", InsertEmail.GetAccount());
		map.put("password", InsertEmail.GetPassword());
		sqlSessionTemplate.insert("insertEMail", map);
	}

	public void DeleteEmailById(String deleteAccount) {
		sqlSessionTemplate.delete("deleteEmailById",deleteAccount);
	}
}
