package com.winterframework.firefrog.common.config.dao;

import com.winterframework.firefrog.common.config.entity.ConfigEntity;

public interface IConfigDao {

	public void saveConfig(ConfigEntity config);

	public String getConfigValueByKey(String module, String key);

	/** 
	* @Title: updateConfig 
	* @Description:保存config信息时如果存在key 则更新
	* @param config
	*/
	public void updateConfig(ConfigEntity config);
}
