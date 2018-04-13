package com.winterframework.modules.web.jsonresult;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * java日期对象经过Jackson库转换成数字
 * 
 * @author henry
 */
public class FirefrogDateDeSerializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonToken currentToken = jp.getCurrentToken();
		if (currentToken == JsonToken.VALUE_NUMBER_INT) {
			return new Date(jp.getNumberValue().longValue());
		} else {
			return null;
		}
	}
}