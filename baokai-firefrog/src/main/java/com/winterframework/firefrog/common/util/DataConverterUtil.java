package com.winterframework.firefrog.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.modules.web.util.JsonMapper;

public class DataConverterUtil {

	private static final ObjectMapper jsonMapper = JsonMapper.nonAlwaysMapper().getMapper();

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

	public static <T> T convertJson2Object(Message json, Class<T> c) {
		try {
			return convertJson2Object(new String(json.getBody(), "UTF-8"), c);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T convertJson2Object(String json, Class<T> c) {
		try {
			return jsonMapper.readValue(json, c);
		} catch (IOException e) {
			logger.error("convertJson2Object error!", e);
		}
		return null;
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

	public static Object convertJson2Object(String json, Class<?> c1, Class<?> c2, Class<?> c3) {
		Object o = null;
		try {
			JavaType javaType = jsonMapper.getTypeFactory().constructParametricType(c1, c2, c3);
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

	public static Date convertLong3Date(String unixTimestamp) {
		try {
			DateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");

			if (unixTimestamp != null) {
				return dt.parse(unixTimestamp);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
