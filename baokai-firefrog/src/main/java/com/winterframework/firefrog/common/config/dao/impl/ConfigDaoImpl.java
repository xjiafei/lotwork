package com.winterframework.firefrog.common.config.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.config.dao.IConfigDao;
import com.winterframework.firefrog.common.config.dao.vo.Config;
import com.winterframework.firefrog.common.config.dao.vo.VOConverter;
import com.winterframework.firefrog.common.config.entity.ConfigEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("configDaoImpl")
public class ConfigDaoImpl extends BaseIbatis3Dao<Config> implements IConfigDao {

	@Override
	public void saveConfig(ConfigEntity configEntity) {
		Config config = VOConverter.configEntity2Config(configEntity);
		insert(config);

	}

	@Override
	public String getConfigValueByKey(String module, String key) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("module", module);
		map.put("key", key);

		String str= this.sqlSessionTemplate.selectOne("getConfigValueByKey", map);
		return str;
	}

	@Override
	public void updateConfig(ConfigEntity configEntity) {
		Config config = VOConverter.configEntity2Config(configEntity);
		this.sqlSessionTemplate.update("updateConfig", config);
	}

}
