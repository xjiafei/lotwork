package com.winterframework.firefrog.game.web.validate.business;

import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: GroupCodeSingleBetNumberMapConfig 
* @Description: 玩法组对应的单个投注位数之间的映射 
* @author page
* @date 2013-8-2 下午5:40:48 
*  
*/
public class GroupCodeSingleBetNumberMapConfig {
	private Map<String, String> groupCodeSingleBetNumberMap = new HashMap<String, String>();

	public Map<String, String> getGroupCodeSingleBetNumberMap() {
		return groupCodeSingleBetNumberMap;
	}

	public void setGroupCodeSingleBetNumberMap(Map<String, String> groupCodeSingleBetNumberMap) {
		this.groupCodeSingleBetNumberMap = groupCodeSingleBetNumberMap;
	}

}
