package com.winterframework.firefrog.phone.web.validate.business;

import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: GroupCodeBetContentRegexMapConfig 
* @Description: 玩法组对应的投注内容正则表达式的映射，主要是复式投注正则
* @author david
* @date 2013-8-2 下午5:40:48 
*  
*/
public class GroupCodeBetContentRegexMapConfig {
	private Map<String, String> groupCodeBetContentRegexMap = new HashMap<String, String>();

	public Map<String, String> getGroupCodeBetContentRegexMap() {
		return groupCodeBetContentRegexMap;
	}

	public void setGroupCodeBetContentRegexMap(Map<String, String> groupCodeBetContentRegexMap) {
		this.groupCodeBetContentRegexMap = groupCodeBetContentRegexMap;
	}
}
