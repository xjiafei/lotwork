package com.winterframework.firefrog.common.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.winterframework.modules.web.jsonresult.Response;

public class Response2JsonView extends MappingJackson2JsonView {
	private Response response;

	public Response2JsonView(Response response) {
		this.response = response;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEncoding encoding = getJsonEncoding(request.getCharacterEncoding());
		ObjectMapper objectMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(),
				encoding);

		if (objectMapper.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
			jsonGenerator.useDefaultPrettyPrinter();
		}

		try {
			objectMapper.writeValue(jsonGenerator, this.response);
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

	protected JsonEncoding getJsonEncoding(String charsetName) {
		for (JsonEncoding encoding : JsonEncoding.values()) {
			if (charsetName.equals(encoding.getJavaName())) {
				return encoding;
			}
		}
		return JsonEncoding.UTF8;
	}
}
