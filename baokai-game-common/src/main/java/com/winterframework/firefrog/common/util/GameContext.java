package com.winterframework.firefrog.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 *GameContext
 * @ClassName
 * @Description 上下文
 * @author ibm
 * 2014年9月7日
 */
public class GameContext {
	private Map<String,Object> attrMap; 
	
	public GameContext(){
		this.attrMap=new HashMap<String,Object>();
	}
	public Object get(String key) {
		return this.attrMap.get(key);
	}

	public void set(String key,Object value) {
		this.attrMap.put(key, value);
	}
	
	
	 
}
