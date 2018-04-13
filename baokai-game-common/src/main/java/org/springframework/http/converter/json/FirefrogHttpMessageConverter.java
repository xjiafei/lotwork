/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.http.converter.json;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

public class FirefrogHttpMessageConverter extends MappingJackson2HttpMessageConverter {

	public FirefrogHttpMessageConverter() {
		setSupportedMediaTypes(Arrays.asList(new MediaType("application", "firefrog", DEFAULT_CHARSET), new MediaType(
				"application", "*+firefrog", DEFAULT_CHARSET)));
		getObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		getObjectMapper().setSerializationInclusion(Include.ALWAYS);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		getObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}


	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {

		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator jsonGenerator = this.getObjectMapper().getJsonFactory().createJsonGenerator(outputMessage.getBody(),
				encoding);

		// A workaround for JsonGenerators not applying serialization features
		// https://github.com/FasterXML/jackson-databind/issues/12
		if (this.getObjectMapper().isEnabled(SerializationFeature.INDENT_OUTPUT)) {
			jsonGenerator.useDefaultPrettyPrinter();
		}

		try {

			this.getObjectMapper().writeValue(jsonGenerator, object);
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}
}
