package com.winterframework.firefrog.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.modules.web.util.JsonMapper;

public class JsonUtil {
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
	public static <T,R> Map<T,R> fromJson2Map(String jsonStr,Class<T> clazz1,Class<R> clazz2){
		return jmapper.fromJson(jsonStr, jmapper.createCollectionType(HashMap.class, clazz1,clazz2));
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
