package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import com.winterframework.firefrog.game.dao.vo.ActivityConfig;


/** 
* @ClassName ActivityConfigResponse 
* @Description 活动配置
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActCfgResponse implements Serializable {

	private static final long serialVersionUID = 51874940978504544L;

	private ActivityConfig config;

	public ActivityConfig getConfig() {
		return config;
	}

	public void setConfigs(ActivityConfig config) {
		this.config = config;
	}
}
