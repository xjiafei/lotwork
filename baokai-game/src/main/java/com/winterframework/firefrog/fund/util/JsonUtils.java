package com.winterframework.firefrog.fund.util;

import java.util.List;

import com.winterframework.modules.web.util.JsonMapper;

public class JsonUtils {
	protected static  JsonMapper jmapper = JsonMapper.nonEmptyMapper();
	
	/**
	 * json数据结构转换对象
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String jsonStr,Class<T> clazz){
		return jmapper.fromJson(jsonStr, clazz);
	}
	/**json数据结构转换List<MyBean>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> fromJson2List(String jsonStr,Class<T> clazz){
		return jmapper.fromJson(jsonStr,
				JsonMapper.nonDefaultMapper().createCollectionType(List.class, clazz));
	}
	
	/**
	 * 对象转换json数据结构
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		return jmapper.toJson(obj);
	}
	
	public static void main(String[] args){
		
	}
	
}
