package com.winterframework.firefrog.game.web.validate.business;

import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: MethodCodeSingleBetNumberMapConfig 
* @Description: 投注方式对应的单个投注位数之间的映射 
* @author page
* @date 2013-8-2 下午5:40:48 
*  
*/
public class MethodCodeSingleBetNumberMapConfig {
	private Map<String, String> methodCodeSingleBetNumberMap = new HashMap<String, String>();

	public Map<String, String> getMethodCodeSingleBetNumberMap() {
		return methodCodeSingleBetNumberMap;
	}

	public void setMethodCodeSingleBetNumberMap(Map<String, String> methodCodeSingleBetNumberMap) {
		this.methodCodeSingleBetNumberMap = methodCodeSingleBetNumberMap;
	}
}
