package com.winterframework.adbox.dao.base;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * 上下文
 * @ClassName
 * @Description
 * @author ibm
 * 2015年8月31日
 */
public class Context { 
	private final static Logger log = LoggerFactory.getLogger(Context.class);
	private Map<String,Object> attrs;
	public Context(){
		attrs=new HashMap<String,Object>();
	};
	public Context(Long userId,Long deviceId){
		attrs=new HashMap<String,Object>();
		set("userId", userId);
		set("deviceId", deviceId);
	}
	public void set(String key,Object value){
		attrs.put(key, value);
	}
	public Object get(String key){
		return attrs.get(key);
	}
	public Map<String, Object> getAttrs() {
		return attrs;
	}
	public Long getUserId(){
		Object userId=get("userId");
		if(userId instanceof Integer){
			return new Long((Integer)userId);
		}else if(userId instanceof Long){
			return (Long)get("userId");
		}
		return null;
	}
	public Long getDeviceId(){
		Object deviceId=get("deviceId");
		if(deviceId instanceof Integer){
			return new Long((Integer)deviceId);
		}else if(deviceId instanceof Long){
			return (Long)get("deviceId");
		}
		return null;
	}
	
}
