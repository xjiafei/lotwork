package com.winterframework.firefrog.common.config.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.config.dao.IConfigDao;
import com.winterframework.firefrog.common.config.dao.impl.ConfigDaoImpl;
import com.winterframework.firefrog.common.config.dao.vo.Config;
import com.winterframework.firefrog.common.config.entity.ConfigEntity;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.orm.dal.ibatis3.BaseManager;

@Service("configServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ConfigServiceImpl extends BaseManager<ConfigDaoImpl, Config> implements IConfigService {

	@Resource(name = "configDaoImpl")
	private IConfigDao confifDao;

	@Override
	public void setEntityDao(ConfigDaoImpl entityDao) {
		this.entityDao = entityDao;

	}

	@Override
	public void saveConfig(ConfigEntity config) {
		String str = getConfigValueByKey(config.getModule(), config.getKey());
		if (StringUtils.isEmpty(str)) {
			confifDao.saveConfig(config);
		} else {
			confifDao.updateConfig(config);
		}

	}

	@Override
	public String getConfigValueByKey(String module, String key) {

		return confifDao.getConfigValueByKey(module, key);

	}

	@Override
	public void saveConfig(String module, String key, String value) {
		ConfigEntity configEntity = new ConfigEntity();
		configEntity.setModule(module);
		configEntity.setKey(key);
		configEntity.setValue(value);
		saveConfig(configEntity);
	}

}
