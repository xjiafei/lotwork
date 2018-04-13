package com.winterframework.firefrog.common.httpjsonclient.impl;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: HttpJsonClientInvoker 
* @Description: 
* @author 你的名字 
* @date 2013-9-24 下午3:55:39 
*  
*/
public class HttpJsonClientInvoker<R, T> implements IHttpJsonClientInvoker<R, T> {

	private static final Logger logger = LoggerFactory.getLogger(HttpJsonClientInvoker.class);

	private ObjectMapper objectMapper = new ObjectMapper();
	
	public HttpJsonClientInvoker(){
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	/**
	* Title: postJsonObject
	* Description:
	* @param url
	* @param params
	* @return
	* @throws ClientProtocolException
	* @throws IOException
	* @throws SecurityException
	* @throws NoSuchMethodException 
	* @see com.winterframework.firefrog.common.http.IHttpJsonClientInvoker#postJsonObject(java.lang.String, com.winterframework.modules.web.jsonresult.Request) 
	*/
	@Override
	public Response<T> postJsonObject(String url, Request<R> params) throws ClientProtocolException, IOException,
			SecurityException, NoSuchMethodException {
		
		if (params.getHead() == null) {
			throw new NullPointerException();
		}
		int second = 10;
		HttpClient httpclient = new DefaultHttpClient();
		if (second > 0) {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, second * 1000);
			HttpConnectionParams.setSoTimeout(httpParams, second * 1000);
		} else {
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
			HttpConnectionParams.setSoTimeout(httpParams, 20000);
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
			if (params != null) {
				String str = toJson(params);
				logger.info("request:" + str);
				ByteArrayEntity mult = new ByteArrayEntity(str.getBytes("UTF-8"));
				mult.setContentType("application/firefrog");
				httpPost.setEntity(mult);
			}

			logger.debug("begin to post url:" + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			ParameterizedType pt = (ParameterizedType) this.getClass()
					.getDeclaredMethod("postJsonObject", String.class, Request.class).getGenericReturnType();
			String result = httpclient.execute(httpPost, responseHandler);
			logger.info("response:" + result);
			return (Response<T>)fromJson(result, this.getJavaType(pt, this.getClass()));
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

	}
	
	public Object fromJson(String jsonString, JavaType javaType) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return objectMapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}
	
	public String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}
	
	/**
	 * Return the Jackson {@link JavaType} for the specified type and context class.
	 * <p>The default implementation returns {@link ObjectMapper#constructType(java.lang.reflect.Type)}
	 * or {@code ObjectMapper.getTypeFactory().constructType(type, contextClass)},
	 * but this can be overridden in subclasses, to allow for custom generic collection handling.
	 * For instance:
	 * <pre class="code">
	 * protected JavaType getJavaType(Type type) {
	 *   if (type instanceof Class && List.class.isAssignableFrom((Class)type)) {
	 *     return TypeFactory.collectionType(ArrayList.class, MyBean.class);
	 *   } else {
	 *     return super.getJavaType(type);
	 *   }
	 * }
	 * </pre>
	 * @param type the type to return the java type for
	 * @param contextClass a context class for the target type, for example a class
	 * in which the target type appears in a method signature, can be {@code null}
	 * signature, can be {@code null}
	 * @return the java type
	 */
	protected JavaType getJavaType(Type type, Class<?> contextClass) {
		return (contextClass != null) ? objectMapper.getTypeFactory().constructType(type, contextClass) : objectMapper
				.constructType(type);
	}
}
