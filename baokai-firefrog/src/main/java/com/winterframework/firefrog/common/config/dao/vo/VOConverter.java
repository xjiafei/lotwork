package com.winterframework.firefrog.common.config.dao.vo;

import com.winterframework.firefrog.common.config.entity.ConfigEntity;
import com.winterframework.firefrog.common.config.entity.MailConfigEnitiy;

public class VOConverter {

	public static Config configEntity2Config(ConfigEntity configEntity) {
		Config config = new Config();
		config.setDefaultValue(configEntity.getDefaultValue());
		config.setFunction(configEntity.getFunction());
		config.setKey(configEntity.getKey());
		config.setMemo(configEntity.getMemo());
		config.setModule(configEntity.getModule());
		config.setType(configEntity.getType());
		config.setValue(configEntity.getValue());
		return config;
	}
	
	public static MailConfigEnitiy  Mailconfg2MailConfigEntity (MailConfig mailConfig)
	{
		MailConfigEnitiy mailconfgEntity = new MailConfigEnitiy ();
		mailconfgEntity.SetAccount(mailConfig.getAccount());
		mailconfgEntity.SetSendmethod (mailConfig.getSendmethod());
		mailconfgEntity.SetSmtpServer(mailConfig.getSmtpserver());
		mailconfgEntity.SetPort(mailConfig.getMailport());
		mailconfgEntity.SetSender(mailConfig.getSender());
		mailconfgEntity.SetPassword (mailConfig.getPassword());
		return mailconfgEntity;
	}

}
