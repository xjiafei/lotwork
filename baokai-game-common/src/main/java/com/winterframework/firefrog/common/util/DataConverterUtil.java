package com.winterframework.firefrog.common.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverterUtil {

	private static final ObjectMapper jsonMapper = new ObjectMapper();

	private static Logger logger = LoggerFactory.getLogger(DataConverterUtil.class);

	public static String convertObject2Json(Object obj) {
		String jsonStr = "";
		try {
			jsonStr = jsonMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("convertObject2Json error!", e);
		}
		return jsonStr;
	}

	public static Object convertJson2Object(String json, Class<?> c) {
		Object o = null;
		try {
			o = jsonMapper.readValue(json, c);
		} catch (IOException e) {
			logger.error("convertJson2Object error!", e);
		}
		return o;
	}
	
	public static Object convertJson2Object(String json, Class<?> c1, Class<?> c2) {
		Object o = null;
		try {
			JavaType javaType = jsonMapper.getTypeFactory().constructParametricType(c1, c2);
			o = jsonMapper.readValue(json, javaType);
		} catch (IOException e) {
			logger.error("convertJson2Object error!", e);
		}
		return o;
	}

	public static long convertDate2Long(Date date) {
		if (date == null) {
			return 0l;
		}
		return date.getTime();
	}

	public static Date convertLong2Date(Long unixTimestamp) {
		if (unixTimestamp != null) {
			return new java.util.Date(unixTimestamp);
		}
		return null;
	}
	
	public static Map<String,Object> convertJson2Map(String json){
		HashMap<String,Object> result = null;
		try {
			result =jsonMapper.readValue(json, new TypeReference<Map<String, Object>>(){});
		} catch (IOException e) {
			logger.error("convertJson2Map error!", e);
		}
		return result;
	}

}
