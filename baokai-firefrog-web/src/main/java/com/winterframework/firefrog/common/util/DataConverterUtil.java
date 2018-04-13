package com.winterframework.firefrog.common.util;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
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

	public static <T> T convertJson2Object(String json, Class<T> c) {
		T o = null;
		try {
			o = jsonMapper.readValue(json, c);
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

}
