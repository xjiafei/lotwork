package com.winterframework.firefrog.common.config.dao.vo;

import com.winterframework.firefrog.common.config.entity.ConfigEntity;

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

}
