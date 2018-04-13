package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;
import java.util.List;


/** 
* @ClassName ActivityConfigResponse 
* @Description 活动配置
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityConfigResponse implements Serializable {

	public ActivityConfigResponse(List<ActivityAwardConfig> configs) {
		super();
		this.configs = configs;
	}

	public ActivityConfigResponse() {
		super();
	}
	
	private static final long serialVersionUID = 51874940978504543L;

	private List<ActivityAwardConfig> configs;

	public List<ActivityAwardConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<ActivityAwardConfig> configs) {
		this.configs = configs;
	}
}
