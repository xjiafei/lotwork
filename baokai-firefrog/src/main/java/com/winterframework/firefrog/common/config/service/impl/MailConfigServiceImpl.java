package com.winterframework.firefrog.common.config.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.config.dao.IMailConfigDao;
import com.winterframework.firefrog.common.config.dao.impl.MailConfigDiaoImple;
import com.winterframework.firefrog.common.config.dao.vo.MailConfig;
import com.winterframework.firefrog.common.config.entity.MailConfigEnitiy;
import com.winterframework.firefrog.common.config.service.IMailConfigService;
import com.winterframework.orm.dal.ibatis3.BaseManager;


@Service("MailconfigServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class MailConfigServiceImpl extends BaseManager<MailConfigDiaoImple, MailConfig> implements IMailConfigService{

	
	@Resource(name = "MailDaoImpl")
	private IMailConfigDao confifDao;

	@Override
	public void setEntityDao(MailConfigDiaoImple entityDao) {
		this.entityDao = entityDao;

	}
	
	public List<MailConfigEnitiy> GetAllMailConfig ()
	{
		return confifDao.GetAllMailConfig();
	}

	public List<MailConfigEnitiy> GetEmailByID(String account) {
		return confifDao.GetEMailByID(account);
	}
	
	public void UpdateEmailByID(MailConfigEnitiy updateMailConfig)
	{
		confifDao.UpdateEmailByID(updateMailConfig);
	}


	public void InsertEmail(MailConfigEnitiy insertEmail) {
		confifDao.InsertEmail(insertEmail);
		
	}

	public void DeleteEmailById(String deleteAccount) {
		confifDao.DeleteEmailById(deleteAccount);
	}
}
