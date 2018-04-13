package com.winterframework.firefrog.common.config.service;

import com.winterframework.firefrog.common.config.entity.ConfigEntity;

public interface IConfigService {
	/** 
	* @Title: saveConfig 
	* @Description:保存配置文件信息
	* @param config
	*/
	public void saveConfig(ConfigEntity config);

	/** 
	* @Title: saveConfig 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param module
	* @param key
	* @param value
	*/
	public void saveConfig(String module, String key, String value);

	/** 
	* @Title: getConfigValueByKey 
	* @Description:获取value的值 key=module+key
	* @param module
	* @param key
	*/
	public String getConfigValueByKey(String module, String key);
	

}
